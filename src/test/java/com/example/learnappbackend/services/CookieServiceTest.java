package com.example.learnappbackend.services;

import static org.junit.jupiter.api.Assertions.*;

import jakarta.servlet.http.Cookie;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class CookieServiceTest {

    private CookieService cookieService;

    @BeforeEach
    void setUp() {
        cookieService = new CookieService();
    }

    @Test
    void testGenerateCookie() {
        String name = "testCookie";
        String value = "cookieValue";
        int expiry = 3600;

        Cookie cookie = cookieService.generateCookie(name, value, expiry);

        assertNotNull(cookie);
        assertEquals(name, cookie.getName());
        assertEquals(value, cookie.getValue());
        assertTrue(cookie.isHttpOnly());
        assertEquals(expiry, cookie.getMaxAge());
    }

    @Test
    void testRemoveCookieFound() {
        String name = "testCookie";
        String value = "cookieValue";
        int expiry = 3600;

        Cookie[] cookies = {new Cookie(name, value)};
        Cookie removedCookie = cookieService.removeCookie(cookies, name);

        assertNotNull(removedCookie);
        assertEquals(name, removedCookie.getName());
        assertEquals(0, removedCookie.getMaxAge());
        assertTrue(removedCookie.isHttpOnly());
    }

    @Test
    void testRemoveCookieNotFound() {
        String name = "testCookie";
        String value = "cookieValue";
        int expiry = 3600;

        Cookie[] cookies = {new Cookie("otherCookie", "otherValue")};
        Cookie removedCookie = cookieService.removeCookie(cookies, name);

        assertNull(removedCookie);
    }

    @Test
    void testRemoveCookieEmptyCookiesArray() {
        String name = "testCookie";
        String value = "cookieValue";
        int expiry = 3600;

        Cookie[] cookies = {};
        Cookie removedCookie = cookieService.removeCookie(cookies, name);

        assertNull(removedCookie);
    }
}
