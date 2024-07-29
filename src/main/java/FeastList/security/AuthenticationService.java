package FeastList.security;

import com.nimbusds.jose.JOSEException;
import org.springframework.security.core.Authentication;

import java.util.Map;

public interface AuthenticationService {

    Map<String, String> loginPrincipal(LoginRequest loginRequest) ;

    String revokeTokens(Map<String,String> tokens);

    Map<String, String> refreshTokens(String refreshToken) ;


    Map<String,String> authenticateOauthUser(String idToken);
}
