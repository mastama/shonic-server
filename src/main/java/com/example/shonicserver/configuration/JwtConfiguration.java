package com.example.shonicserver.configuration;

import com.example.shonicserver.filter.JwtAuthenticationFilter;
import com.example.shonicserver.security.CustomOAuth2User;
import com.example.shonicserver.security.CustomOAuth2UserService;
import com.example.shonicserver.security.OAuth2LoginSuccessHandler;
import com.example.shonicserver.security.UserService;
import com.example.shonicserver.service.JpaUserDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.oauth2.core.oidc.user.DefaultOidcUser;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class JwtConfiguration extends WebSecurityConfigurerAdapter {
    @Autowired
    private JpaUserDetailsService jpaUserDetailsService;
    @Autowired
    private JwtAuthenticationEntryPoint jwtAuthEntryPoint;
    //
    @Autowired
    private JwtAuthenticationFilter jwtFilter;
    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(jpaUserDetailsService);
    }
    //authorization=what u can access with
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.csrf().disable()
                .cors().disable()
                .authorizeRequests()
                .antMatchers("/api/v1/owner/**").permitAll()
                .antMatchers("/oauth2/**").permitAll()
                .antMatchers("/api/v1/otp/*","/api/v1/auth/*",
                        "/*","/api/v1/forgotpassword/*","/api/v1/mock/*",
                        "/api/v1/product/**","/api/v1/brand/**",
                        "/api/v1/category/**").permitAll() // allow this endpoint without authentication
                .anyRequest().authenticated()// for any other request, authentication should be performed
                .and()
                .exceptionHandling().authenticationEntryPoint(jwtAuthEntryPoint)
                .and()
                .oauth2Login()
                    .loginPage("/")
                    .userInfoEndpoint().userService(oAuth2UserService)
                    .and()
                    .successHandler(new AuthenticationSuccessHandler() {
                        @Override
                        public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
                                                            Authentication authentication) throws IOException, ServletException {
                            System.out.println("masuk succsess handler");
                            DefaultOidcUser oauthUser = (DefaultOidcUser) authentication.getPrincipal();
                            String email = oauthUser.getAttribute("email");
                            userService.processOAuthPostLogin(email);
                            System.out.println("email : "+oauthUser.getEmail());

                            response.sendRedirect("/api/v1/auth/home");
                        }
                    })
                .and()
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS); // every request should be independent of other and server does not have to manage session
        http.addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class);
    }
    @Bean
    public BCryptPasswordEncoder getPasswordEncoder(){
        return new BCryptPasswordEncoder();
    }

    @Bean
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }

    @Override
    public void configure(WebSecurity web) throws Exception {
        web.ignoring().antMatchers("/v2/api-docs",
                "/configuration/ui",
                "/swagger-resources/**",
                "/configuration/security",
                "/swagger-ui.html",
                "/webjars/**");
    }
    //oauth2
    @Autowired
    private CustomOAuth2UserService oAuth2UserService;
    @Autowired
    private UserService userService;
   // @Autowired
   // private OAuth2LoginSuccessHandler oAuth2LoginSuccessHandler;
}
