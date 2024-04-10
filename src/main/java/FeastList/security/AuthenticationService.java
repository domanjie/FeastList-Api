package FeastList.security;

import com.nimbusds.jose.JOSEException;
import org.springframework.security.core.Authentication;

import java.util.Map;

public interface AuthenticationService {

    Map<String, String> loginPrincipal(LoginRequest loginRequest) throws JOSEException;

    String revokeTokens(Map<String,String> tokens);

    Map<String, String> refreshTokens(String refreshToken) throws JOSEException;

    Authentication getAuthenticatedUser();
}
