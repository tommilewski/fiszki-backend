package com.example.learnappbackend.services;

import com.example.learnappbackend.exceptions.UserExistsWithEmailException;
import com.example.learnappbackend.exceptions.UserExistsWithUsernameException;
import com.example.learnappbackend.model.User;
import com.example.learnappbackend.model.enums.Role;
import com.example.learnappbackend.model.dto.RegisterRequest;
import com.example.learnappbackend.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.ArrayList;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class RegisterServiceTest {

    @InjectMocks
    private RegisterService registerService;

    @Mock
    private UserRepository userRepository;

    @Mock
    private PasswordEncoder passwordEncoder;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testRegisterNewUser() {
        RegisterRequest request = new RegisterRequest();
        request.setUsername("testUser");
        request.setEmail("test@example.com");
        request.setPassword("password");
        request.setRole(Role.ADMIN);

        when(userRepository.findByUsername(request.getUsername())).thenReturn(Optional.empty());
        when(userRepository.findByEmail(request.getEmail())).thenReturn(Optional.empty());
        when(passwordEncoder.encode(request.getPassword())).thenReturn("encodedPassword");
        when(userRepository.saveAndFlush(any(User.class))).thenAnswer(invocation -> {
            User savedUser = invocation.getArgument(0);
            savedUser.setId(1L);
            return savedUser;
        });

        assertDoesNotThrow(() -> registerService.register(request));

        verify(userRepository, times(1)).findByUsername(request.getUsername());
        verify(userRepository, times(1)).findByEmail(request.getEmail());
        verify(passwordEncoder, times(1)).encode(request.getPassword());
        verify(userRepository, times(1)).saveAndFlush(any(User.class));
    }

    @Test
    void testRegisterUserWithDuplicateUsername() {
        RegisterRequest request = new RegisterRequest();
        request.setUsername("testUser");
        request.setEmail("test@example.com");
        request.setPassword("password");

        when(userRepository.findByUsername(request.getUsername())).thenReturn(Optional.of(new User()));

        assertThrows(UserExistsWithUsernameException.class, () -> registerService.register(request));

        verify(userRepository, times(1)).findByUsername(request.getUsername());
        verify(userRepository, never()).findByEmail(request.getEmail());
        verify(passwordEncoder, never()).encode(request.getPassword());
        verify(userRepository, never()).saveAndFlush(any(User.class));
    }

    @Test
    void testRegisterUserWithDuplicateEmail() {
        RegisterRequest request = new RegisterRequest();
        request.setUsername("testUser");
        request.setEmail("test@example.com");
        request.setPassword("password");

        when(userRepository.findByUsername(request.getUsername())).thenReturn(Optional.empty());
        when(userRepository.findByEmail(request.getEmail())).thenReturn(Optional.of(new User()));

        assertThrows(UserExistsWithEmailException.class, () -> registerService.register(request));

        verify(userRepository, times(1)).findByUsername(request.getUsername());
        verify(userRepository, times(1)).findByEmail(request.getEmail());
        verify(passwordEncoder, never()).encode(request.getPassword());
        verify(userRepository, never()).saveAndFlush(any(User.class));
    }
}
