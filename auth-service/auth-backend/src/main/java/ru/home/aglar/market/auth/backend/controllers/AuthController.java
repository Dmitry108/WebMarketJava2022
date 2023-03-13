package ru.home.aglar.market.auth.backend.controllers;

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
public class AuthController {
    private final AuthenticationManager authenticationManager;
    private final UserService userService;
    private final JwtTokenUtil jwtTokenUtil;

    @PostMapping
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