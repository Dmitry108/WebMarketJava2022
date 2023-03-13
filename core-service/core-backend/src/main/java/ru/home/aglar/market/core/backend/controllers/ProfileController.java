package ru.home.aglar.market.core.backend.controllers;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import ru.home.aglar.market.core.api.ProfileDto;

@RestController
@RequestMapping("/api/v1/profiles")
@RequiredArgsConstructor
@Tag(name = "Profiles", description = "Methods for profiles")
public class ProfileController {

    @GetMapping
    @Operation(summary = "Method for getting info about an user", responses = {@ApiResponse(
            description = "Success response", responseCode = "200",
            content = @Content(schema = @Schema(implementation = ProfileDto.class))
    )})
    public ProfileDto getCurrentUserInfo(@RequestHeader @Parameter(description = "Username", required = true) String username) {
        return new ProfileDto(username);
    }
}