package FeastList.security.config;

import FeastList.security.exceptions.UserNotFoundException;
import FeastList.security.jwt.JwtsProvider;
import FeastList.security.jwt.JwtsTokenFilter;
import FeastList.users.UserRepository;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.authentication.logout.CookieClearingLogoutHandler;
import org.springframework.security.web.authentication.logout.LogoutHandler;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    private  final UserRepository userRepository;
    public SecurityConfig(UserRepository userRepository){
        this.userRepository=userRepository;
    }
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http,AuthenticationManager authenticationManager) throws Exception{

        return
                http
                        .exceptionHandling()
                        .authenticationEntryPoint(
                                (req,res,e)->{res.sendError(HttpServletResponse.SC_UNAUTHORIZED);
                                })
                        .and()
                        .cors()
                        .and()
                        .authorizeHttpRequests()
                        .requestMatchers(
                                "/api/v1/orders/**",
                                "/api/v1/authentication/**",
                                "/api/v1/rle/**")
                        .permitAll()
                        .anyRequest().authenticated()
                        .and()
                        .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.NEVER)
                        .and()
                        .csrf().disable()
                        .addFilterBefore(new JwtsTokenFilter(authenticationManager),UsernamePasswordAuthenticationFilter.class)
                        .build();
    }

    @Bean
    public UserDetailsService userDetailsService(){
        return username -> {
            return userRepository.findById(username)
                    .orElseThrow(
                    ()->new UserNotFoundException(username+ "not found"));
        };
    }
    @Bean
    PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
    @Bean
    public AuthenticationManager authenticationManager(JwtsProvider jwtsProvider, HttpSecurity http)throws Exception{
        return http.getSharedObject(AuthenticationManagerBuilder.class)
                .authenticationProvider(jwtsProvider)
                .getOrBuild();
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
    public WebMvcConfigurer corsConfiguration(){
        return new WebMvcConfigurer() {
            @Override
            public void addCorsMappings(CorsRegistry registry) {
                registry.addMapping("/api/**")
                        .allowCredentials(true)
                        .allowedMethods("*")
                        .allowedOrigins("http://localhost:5173"," http://192.168.43.85:5173");
            }
        };
    }

    @Bean
    public LogoutHandler logoutHandler(){
        return new CookieClearingLogoutHandler("xid");
    }
}
