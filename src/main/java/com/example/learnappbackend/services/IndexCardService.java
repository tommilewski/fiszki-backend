package com.example.learnappbackend.services;

import com.example.learnappbackend.mapper.IndexCardsMapper;
import com.example.learnappbackend.model.IndexCard;
import com.example.learnappbackend.model.User;
import com.example.learnappbackend.model.dto.IndexCardRequest;
import com.example.learnappbackend.model.dto.IndexCardResponse;
import com.example.learnappbackend.repository.IndexCardRepository;
import com.example.learnappbackend.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class IndexCardService {

    private final IndexCardRepository indexCardRepository;
    private final UserRepository userRepository;
    private final IndexCardsMapper mapper;

    public void addNewIndexCard(IndexCardRequest indexCardRequest, String username) {
        User user = userRepository.findByUsername(username).get();

        IndexCard indexCard = this.mapper.map(indexCardRequest, user);

        indexCardRepository.save(indexCard);
        userRepository.save(user);

    }

    public List<IndexCardResponse> findAllByUser(String username) {
        User user = userRepository.findByUsername(username).get();
        List<IndexCard> allByUserUsername = indexCardRepository.findAllByUserUsername(user.getUsername());

        return allByUserUsername.stream()
                .map(mapper::map)
                .collect(Collectors.toList());

    }
}
