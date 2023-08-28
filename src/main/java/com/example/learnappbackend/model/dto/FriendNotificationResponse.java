package com.example.learnappbackend.model.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class FriendNotificationResponse {
    private Long id;
    private String sender;
    private String receiver;
}
