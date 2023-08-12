package com.example.learnappbackend.services;

import com.example.learnappbackend.model.IndexCard;
import com.example.learnappbackend.model.User;
import com.example.learnappbackend.model.dto.IndexCardRequest;
import com.example.learnappbackend.repository.IndexCardRepository;
import com.example.learnappbackend.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Service
@RequiredArgsConstructor
public class IndexCardService {

    private final IndexCardRepository indexCardRepository;
    private final UserRepository userRepository;

    public void addNewIndexCard(IndexCardRequest indexCardRequest, String username) {
        User user = userRepository.findByUsername(username).get();

        IndexCard indexCard = new IndexCard(
                indexCardRequest.getName(),
                indexCardRequest.getType(),
                user,
                indexCardRequest.getWords(),
                indexCardRequest.getTranslations()
        );

        indexCardRepository.save(indexCard);
        userRepository.save(user);

    }

    public Map<String, String> findByName(String name) {
        IndexCard indexCard = indexCardRepository.findByName(name).get();
        return IntStream.range(0, indexCard.getWords().size())
                .boxed()
                .collect(Collectors.toMap(
                        i -> indexCard.getWords().get(i),
                        i -> indexCard.getTranslations().get(i)
                ));

    }
}
