package ru.home.aglar.market.auth.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import ru.home.aglar.market.auth.entities.Role;

import java.time.LocalDateTime;
import java.util.Collection;

@AllArgsConstructor
@Data
public class UserDto {
    private Long id;
    private String username;
    private String password;
    private String email;
    private Collection<Role> roles;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}