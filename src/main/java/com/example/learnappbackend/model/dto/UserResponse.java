package com.example.learnappbackend.model.dto;

import com.example.learnappbackend.model.enums.Role;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class UserResponse {
    private String email;
    private String username;
    private Role role;
}
