 package FeastList.security.jwt;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.Collection;
import java.util.Collections;

 public class JwtsTokenAuthentication implements Authentication {
    private  String name;
    private  String role;

    private  String token;

    private final TokenType tokenType;
    private final boolean isAuthenticated;

    public JwtsTokenAuthentication(String token,TokenType tokenType){
        this.token=token;
        this.tokenType=tokenType;
        isAuthenticated=false;
    }

    public JwtsTokenAuthentication(String name,String role ,TokenType tokenType ){
        this.name=name;
        this.role=role;
        this.tokenType=tokenType;
        isAuthenticated=true;
    }

     public String getToken() {
         return token;
     }

     public String getRole() {
         return role;
     }
     @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
       return Collections.singletonList(new SimpleGrantedAuthority(role));
    }

    @Override
    public Object getCredentials() {
        return null;
    }

    @Override
    public Object getDetails() {
        return null;
    }

    @Override
    public Object getPrincipal() {
        return null;
    }

    @Override
    public boolean isAuthenticated() {
        return isAuthenticated;
    }

    @Override
    public void setAuthenticated(boolean isAuthenticated) throws IllegalArgumentException {

    }

    @Override
    public String getName() {
        return name;
    }

     public TokenType getTokenType() {
         return tokenType;
     }

     public enum  TokenType{
        ACCESS_TOKEN,REFRESH_TOKEN
    }
}
