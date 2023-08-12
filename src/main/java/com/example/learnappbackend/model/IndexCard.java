package com.example.learnappbackend.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Entity
@Table(name = "index_cards")
@Getter
@Setter
@NoArgsConstructor
public class IndexCard {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String type;

    @ManyToOne()
    @JoinColumn(name = "user_id")
    private User user;

    @ElementCollection
    private List<String> words;

    @ElementCollection
    private List<String> translations;

    public IndexCard(String name, String type, User user, List<String> words, List<String> translations) {
        this.name = name;
        this.type = type;
        this.user = user;
        this.words = words;
        this.translations = translations;
    }

}
