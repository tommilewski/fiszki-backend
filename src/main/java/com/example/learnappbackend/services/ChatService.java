package com.example.learnappbackend.services;

import com.example.learnappbackend.exceptions.ChatNotFoundException;
import com.example.learnappbackend.mapper.ChatMapper;
import com.example.learnappbackend.model.Chat;
import com.example.learnappbackend.model.Message;
import com.example.learnappbackend.model.dto.ChatRequest;
import com.example.learnappbackend.model.dto.ChatResponse;
import com.example.learnappbackend.model.dto.MessageRequest;
import com.example.learnappbackend.model.dto.MessageResponse;
import com.example.learnappbackend.repository.ChatRepository;
import com.example.learnappbackend.repository.MessageRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ChatService {

    private final ChatRepository chatRepository;
    private final MessageRepository messageRepository;
    private final ChatMapper mapper;

    public void addChat(ChatRequest chat) {
        chatRepository.save(mapper.map(chat));
    }

    public List<MessageResponse> getAllMessagesInChat(Long chatId) throws ChatNotFoundException {
        Optional<Chat> chat = chatRepository.findById(chatId);

        if (chat.isPresent()) {
            return chat.get().getMessages()
                    .stream()
                    .map(message -> new MessageResponse(message.getSender(), message.getTime(), message.getMessage()))
                    .collect(Collectors.toList());
        }
        throw new ChatNotFoundException("Chat not found");
    }

    public ChatResponse getChatByFriends(String firstUsername, String secondUsername) throws ChatNotFoundException {
        Optional<Chat> chat1 = chatRepository.findByFirstUsernameAndSecondUsername(firstUsername, secondUsername);
        Optional<Chat> chat2 = chatRepository.findByFirstUsernameAndSecondUsername(secondUsername, firstUsername);
        if (chat1.isPresent()) {
            return mapper.map(chat1.get());
        } else if (chat2.isPresent()) {
            return mapper.map(chat2.get());
        }
        throw new ChatNotFoundException("Chat not found");
    }

    public void addMessage(MessageRequest message, Long chatId) throws ChatNotFoundException {
        Chat chat = chatRepository.findById(chatId).orElseThrow(() -> new ChatNotFoundException("Chat not found"));
        List<Message> messages;

        if(chat.getMessages() == null){
            messages = new ArrayList<>();
        } else {
            messages = chat.getMessages();
        }
        Message msg = new Message(message.getSender(), message.getMessage(), chat);
        saveMessage(msg);
        messages.add(msg);
        chat.setMessages(messages);
        chatRepository.save(chat);
    }

    private void saveMessage(Message msg) {
        messageRepository.save(msg);
    }


    public void deleteChatByFriends(String firstUsername, String secondUsername) {
        chatRepository.deleteByFirstUsernameAndSecondUsername(firstUsername, secondUsername);
    }
}
