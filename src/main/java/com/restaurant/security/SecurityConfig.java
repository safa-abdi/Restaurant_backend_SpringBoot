package com.restaurant.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
            .csrf().disable() // Disable CSRF for simplicity if it's a REST API
            .authorizeHttpRequests(auth -> auth
                .requestMatchers("/api/users").permitAll() // Allow public access to this endpoint
                .anyRequest().authenticated() // Require authentication for other endpoints
            );
        return http.build();
    }
}
