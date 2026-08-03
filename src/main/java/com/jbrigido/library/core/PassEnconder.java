package com.jbrigido.library.core;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
public class PassEnconder {

    @Bean
    public static PasswordEncoder getEncoder() {
        return new BCryptPasswordEncoder();
    }
}
