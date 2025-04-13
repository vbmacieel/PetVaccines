package com.project.user_service.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    public static final String[] PUBLIC_ENDPOINTS = {
        "/user/email",
        "/user/password"
    };

    public static final String[] RESTRICTED_ENDPOINTS = {
        "/user/fullname"
    };
}
