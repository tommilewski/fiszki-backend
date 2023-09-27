package com.example.learnappbackend.services;

import com.example.learnappbackend.mapper.IndexCardsMapper;
import com.example.learnappbackend.model.IndexCard;
import com.example.learnappbackend.model.User;
import com.example.learnappbackend.model.dto.IndexCardRequest;
import com.example.learnappbackend.model.dto.IndexCardResponse;
import com.example.learnappbackend.repository.IndexCardRepository;
import com.example.learnappbackend.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

public class IndexCardServiceTest {

    @InjectMocks
    private IndexCardService indexCardService;

    @Mock
    private IndexCardRepository indexCardRepository;

    @Mock
    private UserRepository userRepository;

    @Mock
    private IndexCardsMapper indexCardsMapper;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testAddNewIndexCard() {
        String username = "testUser";
        User user = new User();
        IndexCardRequest indexCardRequest = new IndexCardRequest();
        indexCardRequest.setWords(new ArrayList<>());
        indexCardRequest.setTranslations(new ArrayList<>());

        when(userRepository.findByUsername(username)).thenReturn(Optional.of(user));
        when(indexCardsMapper.map(indexCardRequest, user)).thenReturn(new IndexCard());

        indexCardService.addNewIndexCard(indexCardRequest, username);

        verify(indexCardRepository, times(1)).save(any(IndexCard.class));
        verify(userRepository, times(1)).save(user);
    }

    @Test
    void testFindAllByUser() {
        String username = "testUser";
        User user = new User();
        user.setUsername(username);
        List<IndexCard> indexCards = new ArrayList<>();
        indexCards.add(new IndexCard());
        indexCards.add(new IndexCard());

        when(userRepository.findByUsername(username)).thenReturn(Optional.of(user));
        when(indexCardRepository.findAllByUserUsername(username)).thenReturn(indexCards);
        when(indexCardsMapper.map(any(IndexCard.class))).thenReturn(new IndexCardResponse());

        List<IndexCardResponse> result = indexCardService.findAllByUser(username);

        assertEquals(2, result.size());
    }

    @Test
    void testFindAllPublic() {
        List<IndexCard> indexCards = new ArrayList<>();
        indexCards.add(new IndexCard());
        indexCards.add(new IndexCard());

        when(indexCardRepository.findAllPublic()).thenReturn(indexCards);
        when(indexCardsMapper.map(any(IndexCard.class))).thenReturn(new IndexCardResponse());

        List<IndexCardResponse> result = indexCardService.findAllPublic();

        assertEquals(2, result.size());
    }

    @Test
    void testFindById() {
        Long id = 1L;
        IndexCard indexCard = new IndexCard();
        indexCard.setId(1L);
        IndexCardResponse indexCardResponse = new IndexCardResponse();
        indexCardResponse.setId(1L);

        when(indexCardRepository.findById(id)).thenReturn(Optional.of(indexCard));
        when(indexCardsMapper.map(indexCard)).thenReturn(indexCardResponse);

        IndexCardResponse result = indexCardService.findById(String.valueOf(id));

        assertEquals(id, result.getId());
    }

    @Test
    void testUpdateFavoriteIndexCards() {
        String username = "testUser";
        List<String> updatedList = new ArrayList<>();
        updatedList.add("1");
        updatedList.add("2");
        User user = new User();
        IndexCard indexCard1 = new IndexCard();
        IndexCard indexCard2 = new IndexCard();

        when(userRepository.findByUsername(username)).thenReturn(Optional.of(user));
        when(indexCardRepository.findById(1L)).thenReturn(Optional.of(indexCard1));
        when(indexCardRepository.findById(2L)).thenReturn(Optional.of(indexCard2));

        indexCardService.updateFavoriteIndexCards(username, updatedList);

        assertEquals(2, user.getFavoriteIndexCards().size());
    }

    @Test
    void testGetFavoritesId() {
        String username = "testUser";
        User user = new User();
        user.setFavoriteIndexCards(new ArrayList<>());
        IndexCard indexCard1 = new IndexCard();
        IndexCard indexCard2 = new IndexCard();
        user.getFavoriteIndexCards().add(indexCard1);
        user.getFavoriteIndexCards().add(indexCard2);

        when(userRepository.findByUsername(username)).thenReturn(Optional.of(user));

        List<String> result = indexCardService.getFavoritesId(username);

        assertEquals(2, result.size());
    }

    @Test
    void testGetAllFavoritesByUser() {
        String username = "testUser";
        User user = new User();
        user.setFavoriteIndexCards(new ArrayList<>());
        IndexCard indexCard1 = new IndexCard();
        IndexCard indexCard2 = new IndexCard();
        user.getFavoriteIndexCards().add(indexCard1);
        user.getFavoriteIndexCards().add(indexCard2);

        when(userRepository.findByUsername(username)).thenReturn(Optional.of(user));
        when(indexCardsMapper.map(indexCard1)).thenReturn(new IndexCardResponse());
        when(indexCardsMapper.map(indexCard2)).thenReturn(new IndexCardResponse());

        List<IndexCardResponse> result = indexCardService.getAllFavoritesByUser(username);

        assertEquals(2, result.size());
    }
}
