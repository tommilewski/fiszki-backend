package com.example.learnappbackend.repository;

import com.example.learnappbackend.model.Chat;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ChatRepository extends JpaRepository<Chat, Long> {

    Optional<Chat> findByFirstUsername(String username);

    Optional<Chat> findBySecondUsername(String username);

    Optional<Chat> findByFirstUsernameAndSecondUsername(String firstUsername, String secondUsername);
}
