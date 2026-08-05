package com.jbrigido.library.core;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;

@EnableWebSecurity
@Configuration
public class LibrarySecurity {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) {
        http.csrf(csrf -> csrf.disable())
                .httpBasic(Customizer.withDefaults())
                .formLogin(Customizer.withDefaults())
                .authorizeHttpRequests(auth ->
                        auth.requestMatchers("/auth/**").permitAll()
                                .requestMatchers(HttpMethod.POST, "/authors", "/books").authenticated()
                                .requestMatchers(HttpMethod.PATCH, "/authors", "/books").authenticated()
                                .requestMatchers(HttpMethod.DELETE, "/authors", "/books").authenticated()
                                .requestMatchers(HttpMethod.GET, "/authors", "/authors/**", "/books", "/books/**").permitAll()
                                .anyRequest().authenticated()
                );
        return http.build();
    }
}
