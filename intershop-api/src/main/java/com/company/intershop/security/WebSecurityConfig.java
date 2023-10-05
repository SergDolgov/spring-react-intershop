package com.company.intershop.security;

import com.company.intershop.security.oauth2.CustomAuthenticationSuccessHandler;
import com.company.intershop.security.oauth2.CustomOAuth2UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.HttpStatusEntryPoint;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.servlet.util.matcher.MvcRequestMatcher;
import org.springframework.web.servlet.handler.HandlerMappingIntrospector;

import static org.springframework.security.web.util.matcher.AntPathRequestMatcher.antMatcher;

@RequiredArgsConstructor
@Configuration
@EnableWebSecurity
public class WebSecurityConfig {

    private final CustomOAuth2UserService customOauth2UserService;
    private final CustomAuthenticationSuccessHandler customAuthenticationSuccessHandler;
    private final TokenAuthenticationFilter tokenAuthenticationFilter;

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        return http
                .authorizeHttpRequests(authorizeHttpRequests -> authorizeHttpRequests
                        .requestMatchers(
                                antMatcher(HttpMethod.GET, "/api/products"),
                                antMatcher(HttpMethod.GET,"/api/products/**"),
                                antMatcher(HttpMethod.GET,"/api/products/cart/**"),
                                antMatcher(HttpMethod.GET, "/api/users/me"),
                                antMatcher(HttpMethod.GET, "/api/products/cart")).hasAnyAuthority(ADMIN, USER)
                        .requestMatchers(
                                antMatcher("/api/users"),
                                antMatcher("/api/users/**")).hasAnyAuthority(ADMIN)
                        .requestMatchers(
                                antMatcher("/public/**"),
                                antMatcher("/login"),
                                antMatcher("/auth/**"),
                                antMatcher("/oauth2/**")).permitAll()
                        .requestMatchers(
                                antMatcher("/"),
                                antMatcher("/error"),
                                antMatcher("/csrf"),
                                antMatcher("/swagger-ui.html"),
                                antMatcher("/swagger-ui/**"),
                                antMatcher("/v3/api-docs"),
                                antMatcher("/v3/api-docs/**")).permitAll()
                        .anyRequest().authenticated())
                .oauth2Login(oauth2Login -> oauth2Login
                        .userInfoEndpoint().userService(customOauth2UserService)
                        .and()
                        .successHandler(customAuthenticationSuccessHandler))
                .logout(l -> l.logoutSuccessUrl("/").permitAll())
                .addFilterBefore(tokenAuthenticationFilter, UsernamePasswordAuthenticationFilter.class)
                .sessionManagement(sessionManagement -> sessionManagement.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .exceptionHandling(exceptionHandling -> exceptionHandling.authenticationEntryPoint(new HttpStatusEntryPoint(HttpStatus.UNAUTHORIZED)))
                .cors(Customizer.withDefaults())
                .csrf(AbstractHttpConfigurer::disable)
                .build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    public static final String ADMIN = "ADMIN";
    public static final String USER = "USER";
}
