package com.example.learnappbackend.model.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class IndexCardResponse {

    private Long id;
    private String name;
    private String type;
    private List<String> words;
    private List<String> translations;
    private String username;
    private List<String> usersWhoFavorite;
}
