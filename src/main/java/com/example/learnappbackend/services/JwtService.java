package com.example.learnappbackend.services;

import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.security.Key;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Service
public class JwtService {
    private final CookieService cookieService;
    private final String SECRET_KEY;


    @Value("${jwt.expired}")
    private int expiredToken;

    @Value("${jwt.refresh}")
    private int refreshToken;

    public JwtService(@Value("${jwt.secret_key}") String secretKey, CookieService cookieService) {
        SECRET_KEY = secretKey;
        this.cookieService = cookieService;
    }

    public void validateToken(HttpServletRequest request, HttpServletResponse response) throws IllegalArgumentException, ExpiredJwtException {
        String token = null;
        String refresh = null;
        if (request.getCookies() == null) {
            throw new IllegalArgumentException("Token can't be null");
        }

        for (Cookie value: Arrays.stream(request.getCookies()).toList()) {
            if (value.getName().equals("Authorization")) {
                token = value.getValue();
            } else if (value.getName().equals("refresh")) {
                refresh = value.getValue();
            }
        }

        try {
            validToken(token);
        } catch (IllegalArgumentException | ExpiredJwtException exception) {
            validToken(refresh);
            Cookie refreshCookie = cookieService.generateCookie("refresh", refreshToken(refresh, refreshToken), refreshToken);
            Cookie cookie = cookieService.generateCookie("Authorization", refreshToken(refresh, expiredToken), expiredToken);
            response.addCookie(cookie);
            response.addCookie(refreshCookie);
        }
    }

    private void validToken(String token) throws IllegalArgumentException, ExpiredJwtException {
        Jwts
                .parserBuilder()
                .setSigningKey(getSignKey())
                .build()
                .parseClaimsJws(token);

    }

    private Key getSignKey() {
        byte[] keyBytes = Decoders.BASE64.decode(SECRET_KEY);
        return Keys.hmacShaKeyFor(keyBytes);
    }

    private String generateTokenHelper(String username, int expiry) {
        Map<String, Object> claims = new HashMap<>();
        return createToken(claims, username, expiry);
    }

    public String generateToken(String username, int expiry) {
        return generateTokenHelper(username, expiry);
    }

    public String createToken(Map<String, Object> claims, String username, int expiry) {
        return Jwts
                .builder()
                .setClaims(claims)
                .setSubject(username)
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + expiry))
                .signWith(getSignKey(), SignatureAlgorithm.HS256)
                .compact();
    }

    public String getSubject(String token) {
        return Jwts
                .parser()
                .setSigningKey(SECRET_KEY)
                .parseClaimsJws(token)
                .getBody()
                .getSubject();
    }

    private String refreshToken(String token, int expiry) {
        String username = getSubject(token);
        return generateToken(username, expiry);
    }

}
