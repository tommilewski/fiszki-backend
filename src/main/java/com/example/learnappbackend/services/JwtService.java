package com.example.learnappbackend.services;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Service
public class JwtService {

    private final String SECRET_KEY;

    public JwtService(@Value("${jwt.secret_key}") String SECRET_KEY) {
        this.SECRET_KEY = SECRET_KEY;
    }

    private Key getSigningKey() {
        return Keys.hmacShaKeyFor(Decoders.BASE64.decode(SECRET_KEY));
    }

    private String createToken(Map<String, Object> claims, String username, int expiry) {
        return Jwts
                .builder()
                .setClaims(claims)
                .setSubject(username)
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + expiry))
                .signWith(getSigningKey(), SignatureAlgorithm.HS256)
                .compact();
    }

    public void validToken(String token) {
        Jwts
                .parserBuilder()
                .setSigningKey(getSigningKey())
                .build()
                .parseClaimsJws(token);
    }

    public String generateToken(String username, int expiry) {
        Map<String, Object> claims = new HashMap<>();
        return createToken(claims, username, expiry);
    }

    public String getSubject(String token) {
        return Jwts
                .parser()
                .setSigningKey(SECRET_KEY)
                .parseClaimsJws(token)
                .getBody()
                .getSubject();
    }

    public String refreshToken(String token, int expiry) {
        String username = getSubject(token);
        return generateToken(username, expiry);
    }

}
