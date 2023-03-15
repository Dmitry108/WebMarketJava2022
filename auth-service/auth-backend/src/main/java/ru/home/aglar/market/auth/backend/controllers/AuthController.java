package ru.home.aglar.market.auth.backend.controllers;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import ru.home.aglar.auth.api.JwtRequest;
import ru.home.aglar.auth.api.JwtResponse;
import ru.home.aglar.market.auth.backend.config.JwtTokenUtil;
import ru.home.aglar.market.auth.backend.services.UserService;
import ru.home.aglar.market.common.exceptions.AppError;

@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
//@CrossOrigin("*")
@Tag(name = "Authorization", description = "Methods for authorization")
public class AuthController {
    private final AuthenticationManager authenticationManager;
    private final UserService userService;
    private final JwtTokenUtil jwtTokenUtil;

    @PostMapping
    @Operation(summary = "Method of getting token by username and password", responses = {
            @ApiResponse(description = "Success response", responseCode = "200",
                    content = @Content(schema = @Schema(implementation = JwtResponse.class))),
            @ApiResponse(description = "Authenticate exceptions", responseCode = "401",
                    content = @Content(schema = @Schema(implementation = AppError.class))),
            @ApiResponse(description = "Username not found", responseCode = "404",
                    content = @Content(schema = @Schema(implementation = AppError.class)))})
    public ResponseEntity<?> createAuthToken(@RequestBody JwtRequest authRequest) {
        try {
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
                    authRequest.getUsername(), authRequest.getPassword()));
        } catch (BadCredentialsException e) {
            return new ResponseEntity<>(new AppError("INCORRECT_USERNAME_OR_PASSWORD",
                    "Incorrect username or password"), HttpStatus.UNAUTHORIZED);
        }
        UserDetails userDetails = userService.loadUserByUsername(authRequest.getUsername());
        String token = jwtTokenUtil.generateToken(userDetails);
        return ResponseEntity.ok(new JwtResponse(token));
    }
}