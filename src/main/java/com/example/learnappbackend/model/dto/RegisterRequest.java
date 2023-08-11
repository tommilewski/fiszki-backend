package com.example.learnappbackend.model.dto;

import com.example.learnappbackend.model.enums.Role;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class RegisterRequest {
    private String email;
    private String username;
    private String password;
    private Role role;
}
