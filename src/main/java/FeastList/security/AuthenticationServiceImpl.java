package FeastList.security;

import FeastList.security.jwt.JwtsProvider;
import FeastList.security.jwt.JwtsTokenAuthentication;
import FeastList.security.jwt.JwtsTokenService;
import FeastList.security.jwt.JwtsTokenServiceImpl;
import com.nimbusds.jose.JOSEException;
import com.nimbusds.jose.Payload;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Stack;

@Service
public class AuthenticationServiceImpl implements AuthenticationService{
    @Value("${ACCESS_TOKEN_EXPIRATION_TIME}")
    private  long ACCESS_TOKEN_EXPIRATION_TIME;
    @Value("${REFRESH_TOKEN_EXPIRATION_TIME}")
    private  long REFRESH_TOKEN_EXPIRATION_TIME;
    @Autowired
    private JwtsProvider provider;
    @Value("${REFRESH_TOKEN_SECRET}")
    private   String REFRESH_TOKEN_SECRET;
    @Value("${ACCESS_TOKEN_SECRET}")
    private   String ACCESS_TOKEN_SECRET;

    private final JwtsTokenService jwtsTokenService;
    private final AuthenticationManager authenticationManager;



    public AuthenticationServiceImpl(AuthenticationManager authenticationManager, JwtsTokenService jwtsTokenService){

        this.authenticationManager=authenticationManager;


        this.jwtsTokenService = jwtsTokenService;
    }
    @Override
    public Map<String, String> loginPrincipal(LoginRequest loginRequest) throws JOSEException {
        UsernamePasswordAuthenticationToken token= UsernamePasswordAuthenticationToken.unauthenticated(loginRequest.username(),loginRequest.password());

        Authentication authentication=authenticationManager.authenticate(token);

        SecurityContext context= SecurityContextHolder.createEmptyContext();

        context.setAuthentication(authentication);

        SecurityContextHolder.setContext(context);

        return getTokens(authentication);

    }

    @Override
    public String revokeTokens(Map<String,String> tokens) {
        //perform blacklist of tokens using redis
        return "successful logout";
    }
    //get new set of tokens using refreshToken
    @Override
    public Map<String, String> refreshTokens(String refreshToken) throws JOSEException {
        AuthenticationManager authenticationManager1=new ProviderManager(provider);

        JwtsTokenAuthentication unAuthenticated =new JwtsTokenAuthentication(refreshToken, JwtsTokenAuthentication.TokenType.REFRESH_TOKEN);

       Authentication authenticated= authenticationManager1.authenticate(unAuthenticated);

       return getTokens(authenticated);
    }

    @Override
    public String getAuthenticatedUserId() {
        return SecurityContextHolder.getContext().getAuthentication().getName();
    }

    private Map<String,String>  getTokens(Authentication authentication) throws JOSEException {

        Map<String,Object> accessTokenPayload=new HashMap<>();

        accessTokenPayload.put("exp",new Date().getTime()+ACCESS_TOKEN_EXPIRATION_TIME*1000);

        accessTokenPayload.put("username",authentication.getName());

        accessTokenPayload.put("role",authentication.getAuthorities().iterator().next().getAuthority());

        Map<String,Object> refreshTokenPayload=new HashMap<>(accessTokenPayload);

        refreshTokenPayload.replace("exp",new Date().getTime()+REFRESH_TOKEN_EXPIRATION_TIME*1000);

        String accessToken=jwtsTokenService.createToken(new Payload(accessTokenPayload),ACCESS_TOKEN_SECRET);

        String refreshToken=jwtsTokenService.createToken(new Payload(refreshTokenPayload),REFRESH_TOKEN_SECRET);
        Map<String,String> tokens=new HashMap<>();
        tokens.put("accessToken",accessToken);
        tokens.put("refreshToken",refreshToken);
        return tokens;
    }


}
