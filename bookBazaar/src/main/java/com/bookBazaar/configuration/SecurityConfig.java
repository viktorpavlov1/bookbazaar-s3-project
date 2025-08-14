// SecurityConfig.java
package com.bookBazaar.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.List;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
public class SecurityConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http, JwtRequestFilter jwtFilter) throws Exception {
        http
                .cors(cors -> {}) // <-- enable CORS handling
                .csrf(csrf -> csrf.disable())
                .authorizeHttpRequests(auth -> auth
                        // Permit all preflight requests
                        .requestMatchers(HttpMethod.OPTIONS, "/**").permitAll()

                        .requestMatchers("/api/users/login").permitAll()
                        .requestMatchers("/api/users/specificUser/**").permitAll()
                        .requestMatchers("/api/users/newUser").permitAll()
                        .requestMatchers("/api/books/all").permitAll()

                        // Protect book mutations (role must be admin)
                        .requestMatchers(HttpMethod.POST, "/api/books/new").hasRole("admin")
                        .requestMatchers(HttpMethod.PUT, "/api/books/update").hasRole("admin")
                        .requestMatchers(HttpMethod.DELETE, "/api/books/**").hasRole("admin")

                        .anyRequest().authenticated()
                )
                .addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }

    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration config = new CorsConfiguration();
        // Your frontend origin:
        config.setAllowedOrigins(List.of("http://localhost:3000"));
        config.setAllowedMethods(List.of("GET", "POST", "PUT", "DELETE", "OPTIONS"));
        config.setAllowedHeaders(List.of("*"));
        // expose Authorization if you need to read it client-side
        config.setExposedHeaders(List.of("Authorization", "Content-Type"));
        // If you ever need cookies/credentials across origins:
        // config.setAllowCredentials(true);

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", config);
        return source;
    }
}
