package FeastList.passwordReset;


import FeastList.users.Gender;
import FeastList.users.Role;
import FeastList.users.User;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class JdbcPasswordResetRepositoryImpl implements PasswordResetRepository {
    private final NamedParameterJdbcTemplate jdbcTemplate;
    public JdbcPasswordResetRepositoryImpl(NamedParameterJdbcTemplate jdbcTemplate){
        this.jdbcTemplate=jdbcTemplate;
    }
    @Override
    public void delete(String resetCode) {
        String query= """
                DELETE FROM password_reset
                WHERE reset_code=:resetCode
                """;
        MapSqlParameterSource params=new MapSqlParameterSource()
                .addValue(":resetCode",resetCode);
        jdbcTemplate.update(query,params);
    }

    @Override
    public PasswordReset save(PasswordReset passwordReset) {
        String query= """
                INSERT INTO  password_reset (reset_code,ttl,user)
                VALUES (:resetCode,:ttl,:user)
                """;
        MapSqlParameterSource params =new MapSqlParameterSource()
                .addValue("resetCode",passwordReset.getPasswordResetCode())
                .addValue("ttl",passwordReset.getTTL())
                .addValue("user",passwordReset.getUser().getUserId());
        jdbcTemplate.update(query,params);
        return  passwordReset;
    }

    @Override
    public PasswordReset findByResetCode(String resetCode) {
        String query= """
                SELECT user_id , password , date_joined , phone_number,
                location , state , city , street , zip ,user_role , is_enabled ,
                avatar_url, vendor_name, first_name, last_name, gender , reset_code , ttl
                FROM
                password_reset
                JOIN users on user=user_id
                LEFT JOIN client_runner_detail ON user_id=id
                LEFT JOIN vendor_detail  ON user_id=vendor_id
                WHERE reset_code=:resetCode;
                """;
        MapSqlParameterSource param=new MapSqlParameterSource()
                .addValue("reset_code",resetCode);


        return jdbcTemplate.queryForObject(query,param,getRowMapper());
    }
    private RowMapper<PasswordReset>  getRowMapper(){
        return (rs,rowNum)-> {
            User user = User.builder()
                    .userId(rs.getString("user_id"))
                    .password(rs.getString("password"))
                    .dateJoined(rs.getTimestamp("date_joined"))
                    .phoneNumber(rs.getString("phone_number"))
                    .location(rs.getString("location"))
                    .state(rs.getString("state"))
                    .city(rs.getString("city"))
                    .street(rs.getString("street"))
                    .zip(rs.getString("zip"))
                    .role(Role.valueOf(rs.getString("role")))
                    .isEnabled(rs.getBoolean("is_enabled"))
                    .avatarUrl(rs.getString("avatar_url"))
                    .vendorName(rs.getString("vendor_name"))
                    .firstName(rs.getString("first_name"))
                    .lastName(rs.getString("last_name"))
                    .gender(Gender.valueOf(rs.getString("gender")))
                    .build();
            PasswordReset passwordReset = new PasswordReset();
            passwordReset.setUser(user);
            passwordReset.setPasswordResetCode(rs.getString("reset_code"));
            passwordReset.setTTL(rs.getLong("ttl"));
            return passwordReset;
        };
    }
}
