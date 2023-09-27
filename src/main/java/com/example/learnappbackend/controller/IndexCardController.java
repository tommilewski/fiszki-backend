package com.example.learnappbackend.controller;

import com.example.learnappbackend.model.dto.IndexCardRequest;
import com.example.learnappbackend.model.dto.IndexCardResponse;
import com.example.learnappbackend.services.IndexCardService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/index-cards")
@CrossOrigin(originPatterns = {"http://localhost:8080", "http://localhost:4200"})
public class IndexCardController {

    private final IndexCardService indexCardService;
    @PostMapping("/add")
    public void add(@RequestBody IndexCardRequest indexCardRequest, @RequestParam String username) {
        indexCardService.addNewIndexCard(indexCardRequest, username);

    }

    @GetMapping("/getAll")
    public List<IndexCardResponse> getAllByUser(@RequestParam String username) {
        return indexCardService.findAllByUser(username);

    }

    @GetMapping("/all")
    public List<IndexCardResponse> getAllPublic() {
        return indexCardService.findAllPublic();
    }

    @GetMapping("/get/{id}")
    public IndexCardResponse getById(@PathVariable String id) {
        return indexCardService.findById(id);
    }

    @GetMapping("/get-all/favorites/{username}")
    public List<IndexCardResponse> getAllFavoritesByUser(@PathVariable String username) {
        return indexCardService.getAllFavoritesByUser(username);
    }

    @GetMapping("/get/favorite/{username}")
    public List<String> getFavoritesId(@PathVariable String username) {
        return indexCardService.getFavoritesId(username);
    }

    @PatchMapping("/update/favorite/{username}")
    public void updateFavoritesIndexCards(@PathVariable String username, @RequestBody List<String> updatedList) {
        indexCardService.updateFavoriteIndexCards(username, updatedList);
    }
}
