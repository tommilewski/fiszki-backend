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
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class ChatServiceTest {

    @InjectMocks
    private ChatService chatService;

    @Mock
    private ChatRepository chatRepository;

    @Mock
    private MessageRepository messageRepository;

    @Mock
    private ChatMapper chatMapper;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testAddChat() {
        ChatRequest chatRequest = new ChatRequest();
        Chat chat = new Chat();
        when(chatMapper.map(chatRequest)).thenReturn(chat);

        chatService.addChat(chatRequest);

        verify(chatRepository, times(1)).save(chat);
    }

    @Test
    void testGetAllMessagesInChat() throws ChatNotFoundException {
        Long chatId = 1L;
        Chat chat = new Chat();
        Message message = new Message();
        message.setSender("sender");
        message.setMessage("message");
        List<Message> messages = new ArrayList<>();
        messages.add(message);
        chat.setMessages(messages);
        when(chatRepository.findById(chatId)).thenReturn(Optional.of(chat));

        List<MessageResponse> messageResponses = chatService.getAllMessagesInChat(chatId);

        assertEquals(1, messageResponses.size());
        assertEquals("sender", messageResponses.get(0).getSender());
        assertEquals("message", messageResponses.get(0).getMessage());
    }

    @Test
    void testGetAllMessagesInChatChatNotFoundException() {
        Long chatId = 1L;
        when(chatRepository.findById(chatId)).thenReturn(Optional.empty());

        assertThrows(ChatNotFoundException.class, () -> chatService.getAllMessagesInChat(chatId));
    }

    @Test
    void testGetChatByFriends() throws ChatNotFoundException {
        String firstUsername = "user1";
        String secondUsername = "user2";
        Chat chat = new Chat(1L, firstUsername, secondUsername, new ArrayList<>());
        ChatResponse chatResponse = new ChatResponse(1L, firstUsername, secondUsername, new ArrayList<>());
        when(chatRepository.findByFirstUsernameAndSecondUsername(firstUsername, secondUsername)).thenReturn(Optional.of(chat));
        when(chatMapper.map((Chat) any())).thenReturn(chatResponse);

        ChatResponse response = chatService.getChatByFriends(firstUsername, secondUsername);

        assertEquals("user1", response.getFirstUsername());
        assertEquals("user2", response.getSecondUsername());
        assertEquals(1L, response.getId());
    }

    @Test
    void testGetChatByFriendsReverseOrder() throws ChatNotFoundException {
        String firstUsername = "user1";
        String secondUsername = "user2";
        Chat chat = new Chat(1L, firstUsername, secondUsername, new ArrayList<>());
        ChatResponse chatResponse = new ChatResponse(1L, firstUsername, secondUsername, new ArrayList<>());

        when(chatRepository.findByFirstUsernameAndSecondUsername(firstUsername, secondUsername)).thenReturn(Optional.empty());
        when(chatRepository.findByFirstUsernameAndSecondUsername(secondUsername, firstUsername)).thenReturn(Optional.of(chat));
        when(chatMapper.map((Chat) any())).thenReturn(chatResponse);

        ChatResponse response = chatService.getChatByFriends(firstUsername, secondUsername);

        assertEquals("user1", response.getFirstUsername());
        assertEquals("user2", response.getSecondUsername());
        assertEquals(1L, response.getId());
    }

    @Test
    void testGetChatByFriendsChatNotFoundException() {
        String firstUsername = "user1";
        String secondUsername = "user2";
        when(chatRepository.findByFirstUsernameAndSecondUsername(firstUsername, secondUsername)).thenReturn(Optional.empty());
        when(chatRepository.findByFirstUsernameAndSecondUsername(secondUsername, firstUsername)).thenReturn(Optional.empty());

        assertThrows(ChatNotFoundException.class, () -> chatService.getChatByFriends(firstUsername, secondUsername));
    }

    @Test
    void testAddMessage() throws ChatNotFoundException {
        Long chatId = 1L;
        MessageRequest messageRequest = new MessageRequest();
        Chat chat = new Chat();
        when(chatRepository.findById(chatId)).thenReturn(Optional.of(chat));

        chatService.addMessage(messageRequest, chatId);

        verify(messageRepository, times(1)).save(any(Message.class));
        verify(chatRepository, times(1)).save(any(Chat.class));
    }

    @Test
    void testAddMessageChatNotFoundException() {
        Long chatId = 1L;
        MessageRequest messageRequest = new MessageRequest();
        when(chatRepository.findById(chatId)).thenReturn(Optional.empty());

        assertThrows(ChatNotFoundException.class, () -> chatService.addMessage(messageRequest, chatId));
    }

    @Test
    void testDeleteChatByFriends() {
        String firstUsername = "user1";
        String secondUsername = "user2";

        chatService.deleteChatByFriends(firstUsername, secondUsername);

        verify(chatRepository, times(1)).deleteByFirstUsernameAndSecondUsername(firstUsername, secondUsername);
    }
}
