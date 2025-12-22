package com.example.demo.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {

        http
            // Disable CSRF (important for Swagger & REST)
            .csrf(csrf -> csrf.disable())

            // Allow everything
            .authorizeHttpRequests(auth -> auth
                .requestMatchers(
                    "/swagger-ui.html",
                    "/swagger-ui/**",
                    "/v3/api-docs/**",
                    "/h2-console/**",
                    "/**"
                ).permitAll()
                .anyRequest().permitAll()
            )

            // Disable default login page
            .formLogin(form -> form.disable())

            // Disable HTTP basic auth popup
            .httpBasic(basic -> basic.disable());

        return http.build();
    }
}
