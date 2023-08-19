package com.example.learnappbackend.services;

import com.example.learnappbackend.exceptions.IndexCardNotFoundException;
import com.example.learnappbackend.mapper.IndexCardsMapper;
import com.example.learnappbackend.model.IndexCard;
import com.example.learnappbackend.model.User;
import com.example.learnappbackend.model.dto.IndexCardRequest;
import com.example.learnappbackend.model.dto.IndexCardResponse;
import com.example.learnappbackend.repository.IndexCardRepository;
import com.example.learnappbackend.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class IndexCardService {

    private final IndexCardRepository indexCardRepository;
    private final UserRepository userRepository;
    private final IndexCardsMapper mapper;

    public void addNewIndexCard(IndexCardRequest indexCardRequest, String username) {
        User user = userRepository.findByUsername(username).get();

        IndexCard indexCard = this.mapper.map(indexCardRequest, user);

        indexCardRepository.save(indexCard);
        userRepository.save(user);

    }

    public List<IndexCardResponse> findAllByUser(String username) throws UsernameNotFoundException {
        User user = userRepository.findByUsername(username).orElseThrow(() -> new UsernameNotFoundException("Nie ma takiego użytkownika"));
        List<IndexCard> allByUserUsername = indexCardRepository.findAllByUserUsername(user.getUsername());

        return allByUserUsername.stream()
                .map(mapper::map)
                .collect(Collectors.toList());

    }

    public List<IndexCardResponse> findAllPublic() {
        return indexCardRepository.findAllPublic()
                .stream()
                .map(mapper::map)
                .collect(Collectors.toList());
    }

    public IndexCardResponse findById(String id) {
        IndexCard indexCard = indexCardRepository.findById(Long.valueOf(id)).orElseThrow(() -> new IndexCardNotFoundException("Nie ma takej fiszki"));
        return this.mapper.map(indexCard);

    }
}
