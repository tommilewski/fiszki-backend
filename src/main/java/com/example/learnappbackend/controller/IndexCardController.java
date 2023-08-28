package com.example.learnappbackend.controller;

import com.example.learnappbackend.model.dto.IndexCardRequest;
import com.example.learnappbackend.model.dto.IndexCardResponse;
import com.example.learnappbackend.services.IndexCardService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@CrossOrigin(originPatterns = {"http://localhost:8080", "http://localhost:4200"})
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

    @GetMapping("/api/v1/get-all/favorites/{username}")
    public List<IndexCardResponse> getAllFavoritesByUser(@PathVariable String username) {
        return indexCardService.getAllFavoritesByUser(username);
    }

    @GetMapping("api/v1/get/favorite/{username}")
    public List<String> getFavoritesId(@PathVariable String username) {
        return indexCardService.getFavoritesId(username);
    }

    @PatchMapping("api/v1/update/favorite/{username}")
    public void updateFavoritesIndexCards(@PathVariable String username, @RequestBody List<String> updatedList) {
        indexCardService.updateFavoriteIndexCards(username, updatedList);
    }
}
