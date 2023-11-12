package FeastList.security.jwt;

import com.nimbusds.jose.JOSEException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.stereotype.Component;

import java.text.ParseException;
import java.util.Map;
import java.util.Optional;
@Component
public class JwtsProvider implements AuthenticationProvider {
    @Value("${ACCESS_TOKEN_SECRET}")
    private  String ACCESS_TOKEN_SECRET;

    @Value("${REFRESH_TOKEN_SECRET}")
    private  String REFRESH_TOKEN_SECRET;

    private final JwtsTokenService jwtsTokenService;
    public JwtsProvider(JwtsTokenService jwtsTokenService){
        this.jwtsTokenService=jwtsTokenService;
    }


    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {

        JwtsTokenAuthentication unAuthenticated=(JwtsTokenAuthentication) authentication;

        String token=unAuthenticated.getToken();

        String secret=getSecret(unAuthenticated.getTokenType());

        Optional<Map<String,Object>> optionalTokenPayload;
        try {
            optionalTokenPayload= jwtsTokenService.verifyToken(token,secret);
        } catch (ParseException | JOSEException e) {
            throw new RuntimeException(e);
        }

        return  optionalTokenPayload.map(payload->{

            return new JwtsTokenAuthentication((String) payload.get("username"),(String)payload.get("role"),unAuthenticated.getTokenType());

        }).orElse(null);
    }

    private  String getSecret(JwtsTokenAuthentication.TokenType tokenType) {
        return switch (tokenType) {
            case ACCESS_TOKEN -> ACCESS_TOKEN_SECRET;
            case REFRESH_TOKEN -> REFRESH_TOKEN_SECRET;
            default -> null;
        };
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return JwtsTokenAuthentication.class.isAssignableFrom(authentication);
    }
}
