package com.example.learnappbackend.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "friend_notifications")
@Getter
@Setter
@NoArgsConstructor
public class FriendNotification {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    private User sender;

    @ManyToOne
    private User receiver;

    public FriendNotification(User sender, User receiver) {
        this.sender = sender;
        this.receiver = receiver;
    }
}
