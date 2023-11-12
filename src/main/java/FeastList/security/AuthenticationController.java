package FeastList.security;

import com.nimbusds.jose.JOSEException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.web.authentication.logout.LogoutHandler;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
@RequestMapping(path="/api/v1/authentication" ,produces="application/json")
@CrossOrigin(origins= "http://localhost:5173")
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
    public  String authenticatePrincipal(@RequestBody LoginRequest loginRequest,
                                         HttpServletResponse response,
                                         HttpServletRequest request) throws JOSEException {

        Map<String ,String> tokens= authenticationService.loginPrincipal(loginRequest);

        return newTokens(response,tokens);
    }

    @PostMapping("/logout")
    public String unAuthenticatePrincipal(@AuthenticationPrincipal Authentication authentication,
                                          HttpServletRequest request,HttpServletResponse response) throws JOSEException {

        Map<String,String> tokens=getTokensFromRequest(request);

        logoutHandler.logout(request, response, authentication);

        return authenticationService.revokeTokens(tokens);
    }

    private static Map<String, String> getTokensFromRequest(HttpServletRequest request) {

        Map<String,String> tokens=new HashMap<>();

        var accessToken=request.getHeader("Authorization").substring(7);

        tokens.put("accessToken",accessToken);

        Cookie[] arrRefreshToken=getArrRefreshToken(request);

        if (arrRefreshToken.length!=0){

            var refreshToken=arrRefreshToken[0].getValue();

            tokens.put("refreshToken",refreshToken);
        }

        return tokens;
    }

    @GetMapping("/refresh")
    public String refreshTokens(HttpServletRequest request,HttpServletResponse response) throws JOSEException {

        Cookie[] arrRefreshToken=getArrRefreshToken(request);
        if(arrRefreshToken.length==0){
            throw new JOSEException("refresh token must be present");
        }
        String refreshToken=arrRefreshToken[0].getValue();

        Map<String,String> tokens= authenticationService.refreshTokens(refreshToken);

        return newTokens(response,tokens);

    }

    private String newTokens(HttpServletResponse response, Map<String, String> tokens) {
        Cookie refreshTokenCookie  = buildResponseCookie(tokens.get("refreshToken"));

        response.addCookie(refreshTokenCookie);

        return tokens.get("accessToken");
    }

    private Cookie buildResponseCookie(String refreshToken){

        Cookie cookie=new Cookie("xid",refreshToken);

        cookie.setHttpOnly(true);

        int cookieTTL=(int)(new Date().getTime()+RT_COOKIE_EXPIRATION_TIME);

        cookie.setMaxAge(cookieTTL);

        return cookie;

    }

    private static Cookie[] getArrRefreshToken(HttpServletRequest request){
        return (Cookie[]) Arrays
                .stream(request.getCookies())
                .filter(cookie -> cookie.getName().equals("xid"))
                .distinct().toArray();
    }

}


