package com.example.learnappbackend.repository;

import com.example.learnappbackend.model.FriendNotification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface FriendNotificationRepository extends JpaRepository<FriendNotification, Long> {

    List<FriendNotification> getAllByReceiverUsername(String username);
}
