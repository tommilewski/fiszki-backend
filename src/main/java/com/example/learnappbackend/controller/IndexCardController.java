package com.example.learnappbackend.controller;

import com.example.learnappbackend.model.dto.IndexCardRequest;
import com.example.learnappbackend.services.IndexCardService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequiredArgsConstructor
public class IndexCardController {

    private final IndexCardService indexCardService;

    @PostMapping("/api/v1/add")
    public void add(@RequestBody IndexCardRequest indexCardRequest, @RequestParam String username) {
        indexCardService.addNewIndexCard(indexCardRequest, username);

    }

    @GetMapping("/api/v1/get")
    public Map<String, String> get(@RequestParam String name) {
        return indexCardService.findByName(name);

    }
}
