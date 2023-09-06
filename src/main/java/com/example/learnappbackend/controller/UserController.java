package com.example.learnappbackend.controller;

import com.example.learnappbackend.services.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@CrossOrigin(origins = "*")
public class UserController {

    private final UserService userService;

    @GetMapping("/api/v1/user")
    public List<String> getAllByUsernameLike(@RequestParam String username) {
        return userService.findAllByUsernameLike(username);
    }
}
