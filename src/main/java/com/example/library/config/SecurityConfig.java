package com.example.library.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.example.library.security.JwtFilter;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    private final JwtFilter jwtFilter;

    public SecurityConfig(JwtFilter jwtFilter) {
        this.jwtFilter = jwtFilter;
    }

    @Bean
    public SecurityFilterChain securityFilterChain(
            HttpSecurity http) throws Exception {

        http
                .csrf(csrf -> csrf.disable())

                .sessionManagement(session ->
                        session.sessionCreationPolicy(
                                SessionCreationPolicy.STATELESS
                        )
                )

                .authorizeHttpRequests(auth -> auth

                        .requestMatchers("/auth/**")
                        .permitAll()

                        .requestMatchers(
                                org.springframework.http.HttpMethod.POST,
                                "/books"
                        )
                        .hasAnyRole("ADMIN", "LIBRARIAN")

                        .requestMatchers(
                                org.springframework.http.HttpMethod.PUT,
                                "/books/**"
                        )
                        .hasAnyRole("ADMIN", "LIBRARIAN")

                        .requestMatchers(
                                org.springframework.http.HttpMethod.DELETE,
                                "/books/**"
                        )
                        .hasAnyRole("ADMIN", "LIBRARIAN")

                        .requestMatchers(
                                org.springframework.http.HttpMethod.GET,
                                "/books/**"
                        )
                        .authenticated()

                        .anyRequest()
                        .authenticated()
                )

                .addFilterBefore(
                        jwtFilter,
                        UsernamePasswordAuthenticationFilter.class
                );

        return http.build();
    }
}