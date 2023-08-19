package com.example.learnappbackend.controller;

import com.example.learnappbackend.model.IndexCard;
import com.example.learnappbackend.model.dto.IndexCardRequest;
import com.example.learnappbackend.model.dto.IndexCardResponse;
import com.example.learnappbackend.services.IndexCardService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequiredArgsConstructor
@CrossOrigin
public class IndexCardController {

    private final IndexCardService indexCardService;
    @PostMapping("/api/v1/add")
    public void add(@RequestBody IndexCardRequest indexCardRequest, @RequestParam String username) {
        indexCardService.addNewIndexCard(indexCardRequest, username);

    }

    @GetMapping("/api/v1/getAll")
    public List<IndexCardResponse> getAllByUser(@RequestParam String username) {
        return indexCardService.findAllByUser(username);

    }

    @GetMapping("/api/v1/all")
    public List<IndexCardResponse> getAllPublic() {
        return indexCardService.findAllPublic();
    }

    @GetMapping("/api/v1/get/{id}")
    public IndexCardResponse getById(@PathVariable String id) {
        return indexCardService.findById(id);
    }
}
