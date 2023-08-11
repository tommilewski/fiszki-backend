package com.example.learnappbackend.services;

import com.example.learnappbackend.exceptions.UserExistsWithEmailException;
import com.example.learnappbackend.exceptions.UserExistsWithUsernameException;
import com.example.learnappbackend.model.User;
import com.example.learnappbackend.model.dto.RegisterRequest;
import com.example.learnappbackend.model.enums.Role;
import com.example.learnappbackend.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class RegisterService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public void register(RegisterRequest request) {
        userRepository.findByUsername(request.getUsername()).ifPresent((user) -> {
            throw new UserExistsWithUsernameException("User with username: " + request.getUsername() + " exists");
        });

        userRepository.findByEmail(request.getEmail()).ifPresent((user) -> {
            throw new UserExistsWithEmailException("User with email: " + request.getEmail() + " exists");
        });

        User user = new User();
        user.setEmail(request.getEmail());
        user.setUsername(request.getUsername());
        user.setPassword(passwordEncoder.encode(request.getPassword()));

        if (request.getRole() != null) {
            user.setRole(request.getRole());
        } else {
            user.setRole(Role.USER);
        }

        userRepository.saveAndFlush(user);
    }
}
