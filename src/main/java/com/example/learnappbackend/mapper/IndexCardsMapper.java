package com.example.learnappbackend.mapper;

import com.example.learnappbackend.model.IndexCard;
import com.example.learnappbackend.model.User;
import com.example.learnappbackend.model.dto.IndexCardRequest;
import com.example.learnappbackend.model.dto.IndexCardResponse;
import org.springframework.stereotype.Component;

import java.util.ArrayList;

@Component
public class IndexCardsMapper {

    public IndexCard map(IndexCardRequest indexCardRequest, User user) {
        return new IndexCard(
                indexCardRequest.getName(),
                indexCardRequest.getType(),
                user,
                indexCardRequest.getWords(),
                indexCardRequest.getTranslations(),
                new ArrayList<>()
        );
    }

    public IndexCardResponse map(IndexCard indexCard) {
        return new IndexCardResponse(
                indexCard.getId(),
                indexCard.getName(),
                indexCard.getType(),
                indexCard.getWords(),
                indexCard.getTranslations(),
                indexCard.getUser().getUsername()
        );

    }

}
