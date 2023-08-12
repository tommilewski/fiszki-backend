package com.example.learnappbackend.model.dto;

import lombok.*;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class IndexCardRequest {

    private String name;
    private String type;
    private List<String> words;
    private List<String> translations;
}
