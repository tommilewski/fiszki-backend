package com.example.learnappbackend.security;

import jakarta.servlet.DispatcherType;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        return http
                .csrf(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests(auth -> {
                    auth.dispatcherTypeMatchers(DispatcherType.FORWARD, DispatcherType.ERROR).permitAll();
                    auth.requestMatchers("/api/v1/auth/register","/api/v1/auth/login", "/api/v1/auth/validate", "/api/v1/auth/auto-login",
                            "/api/v1/auth/logged-in", "/api/v1/auth/logout", "/api/v1/add", "/api/v1/get", "/api/v1/getAll", "/api/v1/all",
                            "/api/v1/get/{id}", "api/v1/update/favorite/{username}", "api/v1/get/favorite/{username}", "/api/v1/get-all/favorites/{username}",
                            "/api/v1/friend-requests/send", "/api/v1/friend-requests/accept", "/api/v1/friend-requests/reject", "/api/v1/friend-requests/get-all/{username}",
                            "/api/v1/friends/get-all/{username}", "/api/v1/friends/delete/{username}",
                            "/api/v1/chat/add", "/api/v1/chat/add/message/{chatId}", "api/v1/chat/delete/{firstUsername}/{secondUsername}",
                            "/api/v1/chat/friends/{firstUsername}/{secondUsername}", "/api/v1/chat/all/messages/chat/{chatId}", "/api/v1/user").permitAll();
                    auth.anyRequest().permitAll();
                })
                .build();
    }

    @Bean
    public AuthenticationProvider authenticationProvider(UserDetailsService userDetailsService) {
        DaoAuthenticationProvider daoAuthenticationProvider = new DaoAuthenticationProvider();
        daoAuthenticationProvider.setPasswordEncoder(passwordEncoder());
        daoAuthenticationProvider.setUserDetailsService(userDetailsService);
        return daoAuthenticationProvider;
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration configuration) throws Exception {
        return configuration.getAuthenticationManager();
    }


    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
