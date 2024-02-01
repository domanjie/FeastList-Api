package FeastList.users;

import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.*;

@Repository
public class UserRepositoryJdbcImpl implements UserRepository{
    private final NamedParameterJdbcTemplate jdbcTemplate;

    public UserRepositoryJdbcImpl(NamedParameterJdbcTemplate jdbcTemplate) {
        this.jdbcTemplate=jdbcTemplate;
    }
    @Override
    public String saveUser(User user) {
        String query1= """
                INSERT INTO users(user_id , password , phone_number,
                location ,zip_code , role , is_enabled , avatar_url )
                VALUES (:userId , :password ,:phoneNumber,:location , :zip ,
                :role , :isEnabled, :avatarUrl);
                """;
        MapSqlParameterSource params=getUserMappedParams(user);
        jdbcTemplate.update(query1,params);

        String query2;
        MapSqlParameterSource extraParams;
        if(isVendor(user)){
             query2= """
                    INSERT INTO vendor_detail(vendor_id,vendor_name)
                    VALUES(:vendorId,:vendorName)
                    """;
             extraParams=getVendorParams(user);

        } else {
             query2= """
                    INSERT INTO client_runner_detail(id,first_name,last_name,gender)
                    VALUES(:id,:firstName,:lastName,:gender)
                    """;
             extraParams=getRunnerClientParams(user);
        }
        jdbcTemplate.update(query2,extraParams);
        return user.getUserId();

    }

    @Override
    public Optional<User> findById(String userId) {
        String query= """
                SELECT user_id , password , date_joined , phone_number,
                location ,zip_code , role , is_enabled ,
                avatar_url,vendor_name,first_name,last_name,gender
                FROM
                users
                LEFT JOIN client_runner_detail ON user_id=id
                LEFT JOIN vendor_detail ON user_id=vendor_id
                WHERE user_id=:userId
                """;
        MapSqlParameterSource params=new MapSqlParameterSource()
                .addValue("userId",userId);
        return Optional.ofNullable(jdbcTemplate.queryForObject(query,params,userRowMapper()));

    }

//    to implement later
    @Override
    public User findByActivationCode(String activationCode) {
       return null;
    }


    @Override
    public List<User> getUsersByRole(Role userRole) {
        String query= """
                SELECT user_id , password , date_joined , phone_number,
                location, zip_code , role , is_enabled ,
                avatar_url,vendor_name,first_name,last_name,gender
                FROM
                users
                LEFT JOIN client_runner_detail ON user_id=id
                LEFT JOIN vendor_detail ON user_id=vendor_id
                WHERE role=:userRole
                ORDER BY user_id;
                """;
        MapSqlParameterSource param=new MapSqlParameterSource()
                .addValue(":userRole",userRole.toString());
        return jdbcTemplate.query(query,param,userRowMapper());
    }

    @Override
    public void updateUser(User user) {
        String query= """
                UPDATE users SET
                password=:password,
                phone_number=:phoneNumber,
                location=:location,
                zip_code=:zip,
                role=:role,
                is_enabled=:isEnabled,
                avatar_url=:avatarUrl
                WHERE user_id =:userId
                """;
        MapSqlParameterSource params=getUserMappedParams(user);
        jdbcTemplate.update(query,params);

        String query2;
        MapSqlParameterSource extraParams;
        if(isVendor(user)){
            query2= """
                    UPDATE vendor_detail SET
                    vendor_name=:vendorName
                    WHERE vendor_id=:vendorId
                    """;
            extraParams=getVendorParams(user);
        }
        else {
            query2= """
                    UPDATE client_runner_detail SET
                    first_name =:firstName,
                    last_name=:lastName,
                    gender=:gender
                    WHERE id=:id;
                    """;
            extraParams=getVendorParams(user);
        }
        jdbcTemplate.update(query2,extraParams);
    }

    private RowMapper<User> userRowMapper(){

        return (rs ,rowNum)->{
            return  User.builder()
                    .userId(rs.getString("user_id"))
                    .password(rs.getString("password"))
                    .dateJoined(rs.getTimestamp("date_joined"))
                    .phoneNumber(rs.getString("phone_number"))
                    .location(rs.getString("location"))
                    .zipCode(rs.getString("zip_code"))
                    .role(Role.valueOf(rs.getString("role")))
                    .isEnabled(rs.getBoolean("is_enabled"))
                    .avatarUrl(rs.getString("avatar_url"))
                    .vendorName(rs.getString("vendor_name"))
                    .firstName(rs.getString("first_name"))
                    .lastName(rs.getString("last_name"))
                    .gender(Gender.valueOf(rs.getString("gender")))
                    .build();
        };

    }
    private MapSqlParameterSource getUserMappedParams(User user){
        return new MapSqlParameterSource()
                .addValue("userId",user.getUserId())
                .addValue("password",user.getPassword())
                .addValue("phoneNumber",user.getPhoneNumber())
                .addValue("location",user.getLocation())
                .addValue("zip",user.getZipCode())
                .addValue("role",user.getRole().toString())
                .addValue("isEnabled",user.isEnabled())
                .addValue("avatarUrl",user.getAvatarUrl());
    }
    private MapSqlParameterSource getVendorParams(User user)
    {
        return  new MapSqlParameterSource()
                .addValue("vendorId",user.getUserId())
                .addValue("vendorName",user.getVendorName());
    }
    private MapSqlParameterSource getRunnerClientParams(User user){
      return   new MapSqlParameterSource()
                .addValue("id",user.getUserId())
                .addValue("firstName",user.getFirstName())
                .addValue("lastName",user.getLastName())
                .addValue("gender",user.getGender().toString());
    }
    private boolean isVendor(User user){
       return user.getRole().equals(Role.VENDOR);
    }
}
