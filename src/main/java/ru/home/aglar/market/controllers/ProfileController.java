package ru.home.aglar.market.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.home.aglar.market.dto.ProfileDto;
import ru.home.aglar.market.entities.User;
import ru.home.aglar.market.exceptions.ResourceNotFoundException;
import ru.home.aglar.market.services.UserService;

import java.security.Principal;

@RestController
@RequestMapping("/api/v1/profiles")
@RequiredArgsConstructor
public class ProfileController {
    private final UserService userService;

    @GetMapping
    public ProfileDto getCurrentUserInfo(Principal principal) {
        User user = userService.findUserByUsername(principal.getName()).orElseThrow(() ->
                new ResourceNotFoundException("User does not found"));
        return new ProfileDto(user.getUsername(), user.getEmail());
    }
}