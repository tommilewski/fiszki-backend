package com.example.learnappbackend.services;

import com.example.learnappbackend.model.User;
import com.example.learnappbackend.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class UserServiceTest {

    @InjectMocks
    private UserService userService;

    @Mock
    private UserRepository userRepository;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testLoadUserByUsername() {
        String username = "testUser";
        User user = new User();
        user.setUsername(username);

        when(userRepository.findByUsername(username)).thenReturn(Optional.of(user));

        assertEquals(user, userService.loadUserByUsername(username));

        verify(userRepository, times(1)).findByUsername(username);
    }

    @Test
    void testLoadUserByUsernameNotFound() {
        String username = "nonExistentUser";

        when(userRepository.findByUsername(username)).thenReturn(Optional.empty());

        assertThrows(UsernameNotFoundException.class, () -> userService.loadUserByUsername(username));

        verify(userRepository, times(1)).findByUsername(username);
    }

    @Test
    void testGetAllFriends() {
        String username = "testUser";
        User user = new User();
        user.setUsername(username);
        List<User> friends = new ArrayList<>();
        friends.add(new User());
        friends.add(new User());
        user.setFriends(friends);

        when(userRepository.findByUsername(username)).thenReturn(Optional.of(user));

        List<String> result = userService.getAllFriends(username);

        assertEquals(2, result.size());

        verify(userRepository, times(1)).findByUsername(username);
    }

    @Test
    void testDeleteFriend() {
        String username = "testUser";
        String usernameToDelete = "friendToDelete";
        User user = new User();
        user.setUsername(username);
        User friendToDelete = new User();
        friendToDelete.setUsername(usernameToDelete);
        List<User> friends = new ArrayList<>();
        List<User> friends2 = new ArrayList<>();
        friends.add(friendToDelete);
        friends2.add(user);
        user.setFriends(friends);
        friendToDelete.setFriends(friends2);

        when(userRepository.findByUsername(username)).thenReturn(Optional.of(user));
        when(userRepository.findByUsername(usernameToDelete)).thenReturn(Optional.of(friendToDelete));

        userService.deleteFriend(username, usernameToDelete);

        assertFalse(user.getFriends().contains(friendToDelete));
        assertFalse(friendToDelete.getFriends().contains(user));

        verify(userRepository, times(1)).findByUsername(username);
        verify(userRepository, times(1)).findByUsername(usernameToDelete);
        verify(userRepository, times(1)).save(user);
        verify(userRepository, times(1)).save(friendToDelete);
    }

    @Test
    void testFindAllByUsernameLike() {
        String username = "testUser";
        List<User> users = new ArrayList<>();
        users.add(new User());
        users.add(new User());

        when(userRepository.findAllByUsernameLike(username)).thenReturn(users);

        List<String> result = userService.findAllByUsernameLike(username);

        assertEquals(2, result.size());

        verify(userRepository, times(1)).findAllByUsernameLike(username);
    }
}
