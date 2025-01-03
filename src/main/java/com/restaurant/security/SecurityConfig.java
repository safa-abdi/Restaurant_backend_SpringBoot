package com.restaurant.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
public class SecurityConfig {

    @Autowired
    private JwtUtil jwtUtil;

    @SuppressWarnings({ "deprecation", "removal" })
	@Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http.csrf(csrf -> csrf.disable())
            .authorizeRequests(requests -> requests
                .requestMatchers("/auth/**").permitAll()
                .requestMatchers("/api/meals").permitAll()
                .requestMatchers("/api/stats/daily").permitAll()
                .requestMatchers("/api/stats/monthly").permitAll()
                .requestMatchers("/api/payments").permitAll()
                .requestMatchers("/api/payments/**").permitAll()
                //.requestMatchers("/api/payments").hasRole("STUDENT")
                .requestMatchers("/api/menus/start-date/**").permitAll()
                .requestMatchers("/api/menus").permitAll()
                .requestMatchers("/api/menus/add").hasRole("ADMIN")
                .requestMatchers("/api/ingredients/**").hasRole("ADMIN")
                .requestMatchers("/api/subscription-cards").hasRole("ADMIN")
                .requestMatchers("/api/subscription-cards/*/block").hasRole("ADMIN")
                .requestMatchers("/api/subscription-cards/*/balance").hasRole("ADMIN")
                .requestMatchers("/api/subscription-cards/*/recharge").hasRole("STUDENT")

                .requestMatchers("/api/users/all/**").permitAll()
                .requestMatchers("/api/users/admin/**").hasRole("ADMIN")  
                .anyRequest().authenticated())
            .addFilterBefore(new JwtAuthorizationFilter(jwtUtil), UsernamePasswordAuthenticationFilter.class); 
        return http.build();
    }
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration configuration) throws Exception {
        return configuration.getAuthenticationManager();
    }
}
