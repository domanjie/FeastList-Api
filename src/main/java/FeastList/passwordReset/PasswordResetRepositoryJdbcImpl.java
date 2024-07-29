//package FeastList.passwordReset;
//
//
//import FeastList.users.Gender;
//import FeastList.users.domain.Role;
//import FeastList.users.domain.User;
//import org.springframework.jdbc.core.RowMapper;
//import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
//import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
//import org.springframework.stereotype.Repository;
//
//@Repository
//public class PasswordResetRepositoryJdbcImpl implements PasswordResetRepository {
//    private final NamedParameterJdbcTemplate jdbcTemplate;
//    public PasswordResetRepositoryJdbcImpl(NamedParameterJdbcTemplate jdbcTemplate){
//        this.jdbcTemplate=jdbcTemplate;
//    }
//    @Override
//    public void delete(String resetCode) {
//        var query= """
//                DELETE FROM password_reset
//                WHERE reset_code=:resetCode
//                """;
//        var params=new MapSqlParameterSource()
//                .addValue(":resetCode",resetCode);
//        jdbcTemplate.update(query,params);
//    }
//
//    @Override
//    public void save(PasswordReset passwordReset) {
//        var query= """
//                INSERT INTO  password_reset (reset_code,ttl,user_id)
//                VALUES (:resetCode,:ttl,:user)
//                """;
//        var params =new MapSqlParameterSource()
//                .addValue("resetCode",passwordReset.getPasswordResetCode())
//                .addValue("ttl",passwordReset.getTTL())
//                .addValue("user",passwordReset.getUser().getUserId());
//        jdbcTemplate.update(query,params);
//    }
//
//    @Override
//    public PasswordReset findByResetCode(String resetCode) {
//        var query= """
//                SELECT b.user_id , b.password , b.date_joined , b.phone_number,
//                b.location, b.zip_code ,b.role , b.is_enabled ,
//                b.avatar_url, d.vendor_name, c.first_name, c.last_name, c.gender , a.reset_code , a.ttl
//                FROM
//                password_reset AS a
//                JOIN  users AS b
//                ON a.user_id=b.user_id
//                LEFT JOIN client_runner_detail AS c
//                ON b.user_id=c.id
//                LEFT JOIN vendor_detail  AS d
//                ON b.user_id=d.vendor_id
//                WHERE reset_code=:resetCode;
//                """;
//        var param=new MapSqlParameterSource()
//                .addValue("reset_code",resetCode);
//
//
//        return jdbcTemplate.queryForObject(query,param,getRowMapper());
//    }
//    private RowMapper<PasswordReset>  getRowMapper(){
//        return (rs,rowNum)-> {
////            User user = User.builder()
////                    .userId(rs.getString("user_id"))
////                    .password(rs.getString("password"))
////                    .dateJoined(rs.getTimestamp("date_joined"))
////                    .phoneNumber(rs.getString("phone_number"))
////                    .location(rs.getString("location"))
////                    .zipCode(rs.getString("zip_code"))
////                    .role(Role.valueOf(rs.getString("role")))
////                    .isEnabled(rs.getBoolean("is_enabled"))
////                    .avatarUrl(rs.getString("avatar_url"))
////                    .vendorName(rs.getString("vendor_name"))
////                    .firstName(rs.getString("first_name"))
////                    .lastName(rs.getString("last_name"))
////                    .gender(Gender.valueOf(rs.getString("gender")))
////                    .build();
//
//            return PasswordReset
//                    .builder()
////                    .user(user)
//                    .passwordResetCode(rs.getString("reset_code"))
//                    .TTL(rs.getLong("ttl")).build();
//        };
//    }
//}
