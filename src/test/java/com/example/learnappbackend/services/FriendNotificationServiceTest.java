package com.example.learnappbackend.services;

import com.example.learnappbackend.model.FriendNotification;
import com.example.learnappbackend.model.User;
import com.example.learnappbackend.model.dto.FriendNotificationResponse;
import com.example.learnappbackend.repository.FriendNotificationRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.*;

public class FriendNotificationServiceTest {

    @InjectMocks
    private FriendNotificationService friendNotificationService;

    @Mock
    private FriendNotificationRepository friendNotificationRepository;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testSendFriendRequest() {
        User sender = new User();
        User receiver = new User();

        friendNotificationService.sendFriendRequest(sender, receiver);

        verify(friendNotificationRepository, times(1)).save(any(FriendNotification.class));
    }

    @Test
    void testAcceptFriendRequest() {
        User sender = new User();
        User receiver = new User();
        List<User> friends = new ArrayList<>();
        List<User> friends2 = new ArrayList<>();
        friends.add(receiver);
        friends2.add(sender);
        sender.setFriends(friends);
        receiver.setFriends(friends2);
        FriendNotification friendNotification = new FriendNotification(sender, receiver);

        when(friendNotificationRepository.save(any(FriendNotification.class))).thenReturn(friendNotification);

        friendNotificationService.acceptFriendRequest(friendNotification);

        assertTrue(sender.getFriends().contains(receiver));
        assertTrue(receiver.getFriends().contains(sender));
        verify(friendNotificationRepository, times(1)).delete(any(FriendNotification.class));
    }

    @Test
    void testRejectFriendRequest() {
        FriendNotification friendNotification = new FriendNotification();

        friendNotificationService.rejectFriendRequest(friendNotification);

        verify(friendNotificationRepository, times(1)).delete(friendNotification);
    }

    @Test
    void testGetAllByReceiver() {
        String receiverUsername = "receiverUser";
        User sender = new User();
        sender.setUsername("sender");
        User receiver = new User();
        receiver.setUsername(receiverUsername);
        List<FriendNotification> notifications = new ArrayList<>();
        notifications.add(new FriendNotification(sender, receiver));
        notifications.add(new FriendNotification(sender, receiver));

        when(friendNotificationRepository.getAllByReceiverUsername(receiverUsername)).thenReturn(notifications);

        List<FriendNotificationResponse> notificationResponses = friendNotificationService.getAllByReceiver(receiverUsername);

        assertEquals(2, notificationResponses.size());
    }
}
