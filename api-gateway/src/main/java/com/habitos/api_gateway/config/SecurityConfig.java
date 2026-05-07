package com.habitos.api_gateway.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.web.server.SecurityWebFilterChain;

@Configuration
public class SecurityConfig {

    @Bean
    public SecurityWebFilterChain springSecurityFilterChain(
            ServerHttpSecurity http
    ) {

        return http
                .csrf(ServerHttpSecurity.CsrfSpec::disable)
                .cors(cors -> {})
                .httpBasic(httpBasic -> httpBasic.disable())
                .formLogin(form -> form.disable())
                .authorizeExchange(exchange -> exchange
                        // Allow preflight CORS requests
                        .pathMatchers(HttpMethod.OPTIONS).permitAll()
                        // Public endpoints
                        .pathMatchers("/api/auths/**").permitAll()
                        .pathMatchers("/api/habits/**").permitAll()
                        // Secure everything else
                        .anyExchange().authenticated()
                )
                .build();
    }
}