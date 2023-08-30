package com.example.learnappbackend.model.dto;

import com.example.learnappbackend.model.Message;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class ChatResponse {

    private Long id;
    private String firstUsername;
    private String secondUsername;
    private List<MessageResponse> messages;
}
