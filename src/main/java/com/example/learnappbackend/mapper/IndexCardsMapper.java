package com.example.learnappbackend.mapper;

import com.example.learnappbackend.model.IndexCard;
import com.example.learnappbackend.model.User;
import com.example.learnappbackend.model.dto.IndexCardRequest;
import com.example.learnappbackend.model.dto.IndexCardResponse;
import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Component
public class IndexCardsMapper {

    public IndexCard map(IndexCardRequest indexCardRequest, User user) {
        return new IndexCard(
                indexCardRequest.getName(),
                indexCardRequest.getType(),
                user,
                indexCardRequest.getWords(),
                indexCardRequest.getTranslations()
        );
    }

    public IndexCardResponse map(IndexCard indexCard) {
        return new IndexCardResponse(
                indexCard.getName(),
                indexCard.getType(),
                changeToQuestions(indexCard),
                indexCard.getUser().getUsername());

    }


    private Map<String, String> changeToQuestions(IndexCard indexCard) {
        return IntStream.range(0, indexCard.getWords().size())
                .boxed()
                .collect(Collectors.toMap(
                        i -> indexCard.getWords().get(i),
                        i -> indexCard.getTranslations().get(i)
                ));

    }
}
