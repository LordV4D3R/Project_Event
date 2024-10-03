package com.antran.projectevent.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


@Configuration
public class SecurityConfig {


//    @Bean
//    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
//        http
//            .authorizeHttpRequests((requests) -> requests
//                // Cho phép truy cập Swagger UI mà không cần đăng nhập
//                .requestMatchers("/v3/api-docs/**", "/swagger-ui/**", "/swagger-ui.html").permitAll()
//                .anyRequest().authenticated()
//            )
//            .formLogin((form) -> form.permitAll())
//            .logout((logout) -> logout.permitAll());
//
//        return http.build();
//    }

    // Tùy chọn: Tạo người dùng mặc định
//    @Bean
//    public UserDetailsService userDetailsService() {
//        UserDetails user =
//             User.withDefaultPasswordEncoder()
//                .username("admin")
//                .password("12345")
//                .roles("USER")
//                .build();
//
//        return new InMemoryUserDetailsManager(user);
//    }
}