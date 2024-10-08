package FeastList.security.jwt;


import jakarta.servlet.*;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;


public class JwtsTokenFilter extends OncePerRequestFilter {

    private AuthenticationManager authenticationManager;

    public JwtsTokenFilter(AuthenticationManager authenticationManager){
        this.authenticationManager=authenticationManager;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException,IOException {

        String AUTH_HEADER = "authorization";
        ArrayList<String> headers=Collections.list(request.getHeaderNames());
        System.out.println(headers);
        if (!headers.contains(AUTH_HEADER) )
        {
            filterChain.doFilter(request,response);
            return;
        }
        String authorizationValue =request.getHeader(AUTH_HEADER);

        String accessToken=authorizationValue.substring(7);

        Authentication unAuthenticatedToken =new JwtsTokenAuthentication(accessToken, JwtsTokenAuthentication.TokenType.ACCESS_TOKEN);

        Authentication authenticatedToken= authenticationManager.authenticate(unAuthenticatedToken);

        SecurityContext context= SecurityContextHolder.createEmptyContext();

        context.setAuthentication(authenticatedToken);

        SecurityContextHolder.setContext(context);

        filterChain.doFilter(request,response);
    }
}
