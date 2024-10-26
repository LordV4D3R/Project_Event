package com.antran.projectevent.config;

import com.antran.projectevent.service.AuthenticationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;


@Configuration
@EnableMethodSecurity
public class SecurityConfig {

    @Autowired
    private Filter filter;

    @Autowired
    private AuthenticationService authenticationService;

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        return http
                .csrf(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests(
                    req -> req
                            .requestMatchers("/swagger-ui/**", "/v3/api-docs/**","/api/auth/**").permitAll() // Cho phép truy cập Swagger
                            .requestMatchers("/api/accounts").authenticated() // Các request khác yêu cầu xác thực
            )
                .userDetailsService(authenticationService)
                .sessionManagement(s -> s.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .addFilterBefore(filter, UsernamePasswordAuthenticationFilter.class).build();
    }
}