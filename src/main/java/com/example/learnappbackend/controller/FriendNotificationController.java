package com.example.learnappbackend.controller;

import com.example.learnappbackend.model.dto.FriendNotificationResponse;
import com.example.learnappbackend.repository.FriendNotificationRepository;
import com.example.learnappbackend.repository.UserRepository;
import com.example.learnappbackend.services.FriendNotificationService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/friend-requests")
@CrossOrigin(originPatterns = {"http://localhost:8080", "http://localhost:4200"})
@RequiredArgsConstructor
public class FriendNotificationController {

    private final FriendNotificationService friendNotificationService;
    private final UserRepository userRepository;
    private final FriendNotificationRepository friendNotificationRepository;


    @PostMapping("/send")
    public void sendFriendRequest(@RequestParam String sender, @RequestParam String receiver) {
        friendNotificationService.sendFriendRequest(userRepository.findByUsername(sender).get(), userRepository.findByUsername(receiver).get());
    }

    @PostMapping("/accept")
    public void acceptFriendRequest(@RequestParam Long requestId) {
        friendNotificationService.acceptFriendRequest(friendNotificationRepository.findById(requestId).get());
    }

    @PostMapping("/reject")
    public void rejectFriendRequest(@RequestParam Long requestId) {
        friendNotificationService.rejectFriendRequest(friendNotificationRepository.findById(requestId).get());
    }

    @GetMapping("/get-all/{username}")
    public List<FriendNotificationResponse> rejectFriendRequest(@PathVariable String username) {
        return friendNotificationService.getAllByReceiver(username);
    }
}
