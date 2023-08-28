package com.example.learnappbackend.controller;

import com.example.learnappbackend.services.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/friends")
@CrossOrigin(originPatterns = {"http://localhost:8080", "http://localhost:4200"})
public class FriendsController {

    private final UserService userService;

    @GetMapping("/get-all/{username}")
    public List<String> getAllFriends(@PathVariable String username) {
        return userService.getAllFriends(username);
    }

    @DeleteMapping("/delete/{username}")
    public void deleteFriend(@PathVariable String username, @RequestParam String usernameToDelete) {
        userService.deleteFriend(username, usernameToDelete);
    }
}
