package com.example.learnappbackend.controller;

import com.example.learnappbackend.model.dto.ChatRequest;
import com.example.learnappbackend.model.dto.ChatResponse;
import com.example.learnappbackend.model.dto.MessageRequest;
import com.example.learnappbackend.model.dto.MessageResponse;
import com.example.learnappbackend.services.ChatService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/chat")
@CrossOrigin(originPatterns = {"http://localhost:8080", "http://localhost:4200"})
public class ChatController {

    private final ChatService chatService;

    @PostMapping("/add")
    public void createChat(@RequestBody ChatRequest chat) {
        chatService.addChat(chat);
    }

    @PutMapping("/add/message/{chatId}")
    public void addMessage(@RequestBody MessageRequest message , @PathVariable Long chatId) {
        chatService.addMessage(message, chatId);
    }

    @GetMapping("/all/messages/chat/{chatId}")
    public List<MessageResponse> getAllMessagesInChat(@PathVariable Long chatId) {
        return chatService.getAllMessagesInChat(chatId);
    }

    @GetMapping("/friends/{firstUsername}/{secondUsername}")
    public ChatResponse getChatByFriends(@PathVariable String firstUsername, @PathVariable String secondUsername) {
        return chatService.getChatByFriends(firstUsername, secondUsername);
    }

    @DeleteMapping("/delete/{firstUsername}/{secondUsername}")
    public void deleteChatById(@PathVariable String firstUsername, @PathVariable String secondUsername) {
        chatService.deleteChatByFriends(firstUsername, secondUsername);
    }

}
