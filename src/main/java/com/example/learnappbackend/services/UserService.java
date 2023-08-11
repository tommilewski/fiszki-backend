package com.example.learnappbackend.services;

import com.example.learnappbackend.repository.UserRepository;
import io.jsonwebtoken.ExpiredJwtException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Arrays;

@Service
@RequiredArgsConstructor
public class UserService implements UserDetailsService {

    private final UserRepository userRepository;
    private final JwtService jwtService;
    private final CookieService cookieService;


    @Value("${jwt.expired}")
    private int expiredToken;

    @Value("${jwt.refresh}")
    private int refreshToken;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return userRepository.findByUsername(username).orElseThrow(
                () -> new UsernameNotFoundException("Not found user with username: " + username));
    }

    public String generateToken(String username, int expiry) {
        return jwtService.generateToken(username, expiry);
    }

    public void validateToken(HttpServletRequest request, HttpServletResponse response) throws IllegalArgumentException, ExpiredJwtException {
        String token = null;
        String refresh = null;
        if (request.getCookies() == null) {
            throw new IllegalArgumentException("Token can't be null");
        }
        for (Cookie value : Arrays.stream(request.getCookies()).toList()) {
            if (value.getName().equals("Authorization")) {
                token = value.getValue();
            } else if (value.getName().equals("refresh")) {
                refresh = value.getValue();
            }
        }
        try {
            jwtService.validToken(token);
        } catch (IllegalArgumentException | ExpiredJwtException exception) {
            jwtService.validToken(refresh);
            Cookie refreshCookie = cookieService.generateCookie("refresh", jwtService.refreshToken(refresh, refreshToken), refreshToken);
            Cookie tokenCookie = cookieService.generateCookie("Authorization", jwtService.refreshToken(refresh, expiredToken), expiredToken);
            response.addCookie(tokenCookie);
            response.addCookie(refreshCookie);

        }
    }
}
