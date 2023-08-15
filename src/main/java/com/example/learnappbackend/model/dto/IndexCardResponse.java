package com.example.learnappbackend.model.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;
import java.util.Map;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class IndexCardResponse {

    private String name;
    private String type;
    private Map<String, String> questions;
    private String username;
}
