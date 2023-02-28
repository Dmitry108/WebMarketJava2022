package ru.home.aglar.market.core.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import ru.home.aglar.market.core.dto.ProfileDto;

@RestController
@RequestMapping("/api/v1/profiles")
@RequiredArgsConstructor
public class ProfileController {

    @GetMapping
    public ProfileDto getCurrentUserInfo(@RequestHeader String username) {
        return new ProfileDto(username);
    }
}