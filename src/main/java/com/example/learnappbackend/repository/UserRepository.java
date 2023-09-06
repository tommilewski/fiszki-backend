package com.example.learnappbackend.repository;

import com.example.learnappbackend.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    Optional<User> findByUsername(String username);
    Optional<User> findByEmail(String email);

    @Query(value = "SELECT * FROM users WHERE username LIKE %:username%", nativeQuery = true)
    List<User> findAllByUsernameLike(@Param("username") String username);
}
