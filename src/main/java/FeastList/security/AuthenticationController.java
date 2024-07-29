package FeastList.security;

import com.nimbusds.jose.JOSEException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.apache.coyote.Response;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.repository.query.Param;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.web.authentication.logout.LogoutHandler;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
@RequestMapping(path="/api/v1/authentication" ,produces="application/json")
public class AuthenticationController {
    private final AuthenticationService authenticationService;
    @Value("${REFRESH_TOKEN_EXPIRATION_TIME}")
    private  long RT_COOKIE_EXPIRATION_TIME;
    private final LogoutHandler logoutHandler;

    public AuthenticationController(AuthenticationService authenticationService,
                                    LogoutHandler  logoutHandler) {

        this.authenticationService=authenticationService;
        this.logoutHandler=logoutHandler;

    }

    @PostMapping("/login")
    public  String authenticateUser(@RequestBody LoginRequest loginRequest,
                                         HttpServletResponse response,
                                         HttpServletRequest request)  {

        Map<String ,String> tokens= authenticationService.loginPrincipal(loginRequest);
        return newTokens(response,tokens);
    }

    @GetMapping("/oauth-login")
    public void   authenticateOauthUser(@RequestParam(value = "id_token",required = true)String id_token){
        authenticationService.authenticateOauthUser(id_token);
    }

    @PostMapping("/logout")
    public String logoutUser(
            @AuthenticationPrincipal Authentication authentication,
            HttpServletRequest request,
            HttpServletResponse response,
            @RequestHeader(value = "authorization",required = true)String accessToken,
            @CookieValue("xid") String refreshToken
    ) {


        var tokens=new HashMap<String ,String>();
        tokens.put("accessToken",accessToken);
        tokens.put("refreshToken",refreshToken);
        logoutHandler.logout(request, response, authentication);
        return authenticationService.revokeTokens(tokens);
    }

    @GetMapping("/refresh")
    public ResponseEntity<String> refreshTokens(@CookieValue(required = true,value = "xid")String refreshToken, HttpServletResponse response) throws JOSEException {;
        System.out.println(refreshToken);

        if (refreshToken==null)
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        Map<String,String> tokens= authenticationService.refreshTokens(refreshToken);
        var accessToken= newTokens(response,tokens);
        return ResponseEntity.ok(accessToken);

    }

    private String newTokens(HttpServletResponse response, Map<String, String> tokens) {
        Cookie refreshTokenCookie  = buildResponseCookie(tokens.get("refreshToken"));
        response.addCookie(refreshTokenCookie);
        return tokens.get("accessToken");
    }

    private Cookie buildResponseCookie(String refreshToken){
        Cookie cookie=new Cookie("xid",refreshToken);
        cookie.setHttpOnly(true);
//        cookie.setSecure(false);
        int cookieTTL=(int)(new Date().getTime()+(RT_COOKIE_EXPIRATION_TIME*1000));
        cookie.setMaxAge(cookieTTL);
        return cookie;

    }
    @GetMapping("/unauthorized")
    public ResponseEntity unAuthorized(){
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
    }
}


