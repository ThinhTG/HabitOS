package com.habitos.api_gateway.config;

import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.reactive.CorsWebFilter;
import org.springframework.web.cors.reactive.UrlBasedCorsConfigurationSource;


@Configuration
public class CorsConfig {

                @Value("${app.cors.allowed-origins:http://localhost:5173}")
                private String allowedOrigins;

                @Value("${app.cors.allowed-origin-patterns:}")
                private String allowedOriginPatterns;

    @Bean
    public CorsWebFilter corsWebFilter() {

        CorsConfiguration config = new CorsConfiguration();

        // Frontend URLs (comma-separated)
        List<String> origins = Arrays.stream(allowedOrigins.split(","))
                .map(String::trim)
                .filter(origin -> !origin.isBlank())
                .toList();

        if (!origins.isEmpty()) {
            config.setAllowedOrigins(origins);
        } else {
            config.setAllowedOrigins(List.of("http://localhost:5173"));
        }

        // Optional wildcard patterns (comma-separated), e.g. https://*.vercel.app
        List<String> patterns = Arrays.stream(allowedOriginPatterns.split(","))
                .map(String::trim)
                .filter(pattern -> !pattern.isBlank())
                .toList();

        if (!patterns.isEmpty()) {
            config.setAllowedOriginPatterns(patterns);
        }

        // Allow all headers
        config.setAllowedHeaders(List.of("*"));

        // Allow all methods
        config.setAllowedMethods(List.of(
                "GET",
                "POST",
                "PUT",
                "DELETE",
                "OPTIONS"
        ));

        // Allow credentials (JWT/Cookies)
        config.setAllowCredentials(true);

        UrlBasedCorsConfigurationSource source =
                new UrlBasedCorsConfigurationSource();

        source.registerCorsConfiguration("/**", config);

        return new CorsWebFilter(source);
    }
}