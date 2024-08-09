package FeastList.security;

import FeastList.security.jwt.JwtsProvider;
import FeastList.security.jwt.JwtsTokenAuthentication;
import FeastList.security.jwt.JwtsTokenService;
import FeastList.security.jwt.JwtsTokenServiceImpl;
import FeastList.users.Gender;
import FeastList.users.UserRepository;
import FeastList.users.domain.Client;
import FeastList.users.service.contracts.ClientService;
import FeastList.users.service.contracts.UserService;
import com.google.api.client.googleapis.auth.oauth2.GoogleIdToken;
import com.google.api.client.googleapis.auth.oauth2.GoogleIdTokenVerifier;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.gson.GsonFactory;
import com.nimbusds.jose.JOSEException;
import com.nimbusds.jose.Payload;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.security.GeneralSecurityException;
import java.util.*;

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

    private final UserRepository userRepository;

    private final JwtsTokenService jwtsTokenService;
    private final AuthenticationManager authenticationManager;


    private final GoogleIdTokenVerifier verifier ;

    public AuthenticationServiceImpl( AuthenticationManager authenticationManager,
                                      JwtsTokenService jwtsTokenService,
                                      UserRepository userRepository,
                                      @Value("${CLIENT_ID}")final String CLIENT_ID){
        this.userRepository = userRepository;
        this.authenticationManager=authenticationManager;
        this.jwtsTokenService = jwtsTokenService;
        this.verifier= new GoogleIdTokenVerifier.Builder(new NetHttpTransport(), new GsonFactory())
                .setAudience(Collections.singletonList(CLIENT_ID))
                .build();
    }

    @Override
    public Map<String, String> loginPrincipal(LoginRequest loginRequest)  {
        UsernamePasswordAuthenticationToken token= UsernamePasswordAuthenticationToken.unauthenticated(loginRequest.username(),loginRequest.password());

        Authentication authentication=authenticationManager.authenticate(token);

        SecurityContext context= SecurityContextHolder.createEmptyContext();

        context.setAuthentication(authentication);

        SecurityContextHolder.setContext(context);

        return getTokens(authentication.getName(),authentication.getAuthorities().iterator().next().getAuthority());

    }

    @Override
    public String revokeTokens(Map<String,String> tokens) {
        //perform blacklist of tokens using redis
        return "successful logout";
    }
    //get new set of tokens using refreshToken
    @Override
    public Map<String, String> refreshTokens(String refreshToken)  {
        AuthenticationManager authenticationManager1=new ProviderManager(provider);

        JwtsTokenAuthentication unAuthenticated =new JwtsTokenAuthentication(refreshToken, JwtsTokenAuthentication.TokenType.REFRESH_TOKEN);

       Authentication authenticated= authenticationManager1.authenticate(unAuthenticated);

       return getTokens(authenticated.getName(),authenticated.getAuthorities().iterator().next().getAuthority());
    }
    @Override
    @SneakyThrows
    public Map<String, String> authenticateOauthUser(String idTokenString) {
        GoogleIdToken idToken = verifier.verify(idTokenString);
        if (idToken == null)
            throw new AccessDeniedException("INVALID_ID_TOKEN");


        GoogleIdToken.Payload payload = idToken.getPayload();
        String userId = payload.getSubject();
        System.out.println("User ID: " + userId);
        String email = payload.getEmail();

        //check if user already exists
        var  optionalUser=userRepository.findById(email);
        if(optionalUser.isPresent()){
            var user=optionalUser.get();
            return getTokens(user.getUserId(),user.getAuthorities().iterator().next().getAuthority());
        }else{
            String name = (String) payload.get("name");
            var client = Client.builder().userId(email).isEnabled(true).gender(Gender.MALE).build();
            userRepository.persist(client);
            return getTokens(client.getUserId(),client.getAuthorities().iterator().next().getAuthority());
        }
    }

    private Map<String,String>  getTokens(String username,String role)  {

        Map<String,Object> accessTokenPayload=new HashMap<>();

        accessTokenPayload.put("exp",new Date().getTime()+ACCESS_TOKEN_EXPIRATION_TIME*1000);

        accessTokenPayload.put("username",username);

        accessTokenPayload.put("role",role);

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
