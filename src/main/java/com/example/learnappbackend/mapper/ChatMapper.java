package com.example.learnappbackend.mapper;

import com.example.learnappbackend.model.Chat;
import com.example.learnappbackend.model.IndexCard;
import com.example.learnappbackend.model.User;
import com.example.learnappbackend.model.dto.*;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.stream.Collectors;

@Component
public class ChatMapper {

    public Chat map(ChatRequest chatRequest) {
        return new Chat(
                chatRequest.getFirstUsername(),
                chatRequest.getSecondUsername()
        );
    }

    public ChatResponse map(Chat chat) {
        return new ChatResponse(
                chat.getId(),
                chat.getFirstUsername(),
                chat.getSecondUsername(),
                chat.getMessages()
                        .stream()
                        .map(message -> new MessageResponse(message.getSender(), message.getTime(), message.getMessage()))
                        .collect(Collectors.toList())

        );
    }
}
