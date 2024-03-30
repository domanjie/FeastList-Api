//package FeastList.users.userActivator;
//
//import org.springframework.jdbc.core.RowMapper;
//import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
//import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
//import org.springframework.stereotype.Repository;
//
//import java.util.Optional;
//@Repository
//public class ActivationRepoImpl implements ActivationRepo {
//
//    private final NamedParameterJdbcTemplate jdbcTemplate;
//
//    public ActivationRepoImpl(NamedParameterJdbcTemplate jdbcTemplate) {
//        this.jdbcTemplate = jdbcTemplate;
//    }
//
//    @Override
//    public void SaveActivation(Activation activation) {
//        var query= """
//                INSERT  INTO  activation values(:activationCode,:userId,:ttl);
//                """;
//        var params =new MapSqlParameterSource()
//                .addValue("activationCode",activation.ActivationCode())
//                .addValue("userId", activation.userId())
//                .addValue("ttl",activation.ttl());
//        jdbcTemplate.update(query,params);
//    }
//
//    @Override
//    public Optional<Activation> getByActivationCode(String activationCode) {
//        var query= """
//                SELECT * FROM activation  WHERE activation_code =:activationCode;
//                """;
//        var params =new MapSqlParameterSource()
//                .addValue("activationCode",activationCode);
//        return Optional.ofNullable(jdbcTemplate.queryForObject(query,params,rowMapper()));
//
//    }
//    public RowMapper<Activation> rowMapper(){
//        return  (rs, rowNum) -> {
//            return new Activation(
//                    rs.getString("user_id"),
//                    rs.getString("activationCode"),
//                    rs.getLong("ttl")
//            );
//        };
//    }
//
//}
