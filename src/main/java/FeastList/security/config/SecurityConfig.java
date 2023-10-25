package FeastList.security.config;

import FeastList.security.jwt.JwtsTokenFilter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.authentication.logout.CookieClearingLogoutHandler;
import org.springframework.security.web.authentication.logout.LogoutHandler;


import java.util.ArrayList;
import java.util.List;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http,JwtsTokenFilter filter) throws Exception{

        return
                http
                    .authorizeHttpRequests()
                        .requestMatchers("/api/v1/orders","/api/v1/orders/**","/api/v1/authentication","/api/v1/authentication/**","/").permitAll()
                        .anyRequest().authenticated()
                        .and()
//                        .oauth2Login(Customizer.withDefaults())
                        .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                        .and()
                        .csrf().disable()
                        .addFilterBefore(filter,UsernamePasswordAuthenticationFilter.class)
//                        .oauth2Login(oauth2->oauth2.authorizationEndpoint(endpoint->{endpoint.baseUri("/oauth2/authorize");}))
                        .build();
    }

    @Bean
    public InMemoryUserDetailsManager userDetailsService(){
        PasswordEncoder encoder=passwordEncoder();
        List<UserDetails> usersList=new ArrayList<>();
        usersList.add(new User("buzz",encoder.encode("password"),
                List.of(new SimpleGrantedAuthority("ROLE_USER"))));
        usersList.add(new User("woody",encoder.encode("password"),
                List.of(new SimpleGrantedAuthority("ROLE_USER"))));

        return  new InMemoryUserDetailsManager(usersList);
    }
    @Bean
    PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
    @Bean
    public AuthenticationManager authenticationManager(HttpSecurity http)throws Exception{
        return http.getSharedObject(AuthenticationManagerBuilder.class).build();
    }

    @Bean
    @Order(1)
    public DaoAuthenticationProvider usernamePasswordAuthenticationProvider(){
        DaoAuthenticationProvider daoAuthenticationProvider= new DaoAuthenticationProvider();
        daoAuthenticationProvider.setUserDetailsService(userDetailsService());
        daoAuthenticationProvider.setPasswordEncoder(passwordEncoder());
        return daoAuthenticationProvider;
    }

    @Bean
    public LogoutHandler logoutHandler()
    {
        return new CookieClearingLogoutHandler("xid");
    }
}
