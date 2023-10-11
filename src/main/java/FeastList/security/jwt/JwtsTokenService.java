package FeastList.security.jwt;

import com.nimbusds.jose.JOSEException;
import com.nimbusds.jose.Payload;

import java.text.ParseException;
import java.util.Map;
import java.util.Optional;

public interface JwtsTokenService {
    String createToken(Payload payload ) throws JOSEException;

    Optional<Map<String, Object>> verifyToken(String Token) throws ParseException, JOSEException;


}
