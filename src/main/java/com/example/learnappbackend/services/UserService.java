package com.example.learnappbackend.services;

import com.example.learnappbackend.model.User;
import com.example.learnappbackend.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UserService implements UserDetailsService {

    private final UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return userRepository.findByUsername(username).orElseThrow(
                () -> new UsernameNotFoundException("Not found user with username: " + username));
    }

    public List<String> getAllFriends(String username) {
        User user = userRepository.findByUsername(username).get();
        return user.getFriends()
                .stream()
                .map(User::getUsername)
                .collect(Collectors.toList());

    }


    public void deleteFriend(String username, String usernameToDelete) {
        User user = userRepository.findByUsername(username).get();
        User userToDelete = userRepository.findByUsername(usernameToDelete).get();

        user.getFriends().remove(userToDelete);
        userToDelete.getFriends().remove(user);

        userRepository.save(user);
        userRepository.save(userToDelete);
    }
}
