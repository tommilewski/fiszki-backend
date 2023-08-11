package com.example.learnappbackend.services;

import jakarta.servlet.http.Cookie;
import org.springframework.stereotype.Service;

@Service
public class CookieService {

    public Cookie generateCookie(String name, String value, int expiry) {
        Cookie cookie = new Cookie(name, value);
        cookie.setHttpOnly(true);
        cookie.setMaxAge(expiry);
        return cookie;
    }
}
