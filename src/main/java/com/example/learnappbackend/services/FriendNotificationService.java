package com.example.learnappbackend.services;

import com.example.learnappbackend.model.FriendNotification;
import com.example.learnappbackend.model.User;
import com.example.learnappbackend.model.dto.FriendNotificationResponse;
import com.example.learnappbackend.repository.FriendNotificationRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class FriendNotificationService {

    private final FriendNotificationRepository friendNotificationRepository;
    public void sendFriendRequest(User sender, User receiver) {
        FriendNotification friendNotification = new FriendNotification(sender, receiver);
        friendNotificationRepository.save(friendNotification);
    }

    public void acceptFriendRequest(FriendNotification friendNotification) {;
        User sender = friendNotification.getSender();
        User receiver = friendNotification.getReceiver();

        sender.getFriends().add(receiver);
        receiver.getFriends().add(sender);

        friendNotificationRepository.delete(friendNotification);
    }

    public void rejectFriendRequest(FriendNotification friendNotification) {
        friendNotificationRepository.delete(friendNotification);
    }

    public List<FriendNotificationResponse> getAllByReceiver(String username) {
        List<FriendNotification> notifications = friendNotificationRepository.getAllByReceiverUsername(username);
        return notifications.stream()
                .map(friendNotification -> new FriendNotificationResponse(
                        friendNotification.getId(),
                        friendNotification.getSender().getUsername(),
                        friendNotification.getReceiver().getUsername()
                        ))
                .collect(Collectors.toList());
    }
}
