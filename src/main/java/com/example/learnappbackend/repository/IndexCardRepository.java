package com.example.learnappbackend.repository;

import com.example.learnappbackend.model.IndexCard;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface IndexCardRepository extends JpaRepository<IndexCard, Long> {

    Optional<IndexCard> findByName(String name);
    List<IndexCard> findAllByUserUsername(String username);
}
