package ru.home.aglar.market.auth.backend.controllers;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import org.springframework.web.client.RestTemplate;
import ru.home.aglar.auth.api.JwtRequest;
import ru.home.aglar.auth.api.JwtResponse;
import ru.home.aglar.market.common.exceptions.AppError;

import java.net.URI;
import java.net.URISyntaxException;

@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
@Tag(name = "Authorization", description = "Methods for authorization")
public class AuthController {
    private RestTemplate restTemplate;

    @PostMapping
    @Operation(summary = "Method of getting token by username and password", responses = {
            @ApiResponse(description = "Success response", responseCode = "200",
                    content = @Content(schema = @Schema(implementation = JwtResponse.class))),
            @ApiResponse(description = "Authenticate exceptions", responseCode = "401",
                    content = @Content(schema = @Schema(implementation = AppError.class))),
            @ApiResponse(description = "Username not found", responseCode = "404",
                    content = @Content(schema = @Schema(implementation = AppError.class)))})
    public ResponseEntity<?> createAuthToken(@RequestBody JwtRequest jwtRequest) {
        try {
            HttpEntity<AuthRequest> httpEntity = new HttpEntity<>(new AuthRequest(jwtRequest.getUsername(), jwtRequest.getPassword()));
            ResponseEntity<AuthResponse> authRequestResponseEntity = restTemplate.exchange(
                    new URI("http://localhost:8080/realms/master/protocol/openid-connect/token"),
                    HttpMethod.POST,
                    httpEntity, AuthResponse.class);
            if (authRequestResponseEntity.getStatusCode() == HttpStatus.OK) {
                return ResponseEntity.ok(new JwtResponse(authRequestResponseEntity.getBody().getAccessToken()));
            }
            return new ResponseEntity<>(new AppError("INCORRECT_USERNAME_OR_PASSWORD",
                    "Incorrect username or password"), HttpStatus.UNAUTHORIZED);
        } catch (URISyntaxException e) {
            return new ResponseEntity<>(new AppError("CONNECTION_ERRORS",
                    "Incorrect username or password"), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Data
    @AllArgsConstructor
    public class AuthRequest {
        private final String GRANT_TYPE = "password";
        private final String clientId = "postman";
        private final String clientSecret = "MQOuoVV5NxF1vqsMuKaoSyN8L65TG1tS";
        private String username;
        private String password;
    }

    @Data
    @AllArgsConstructor
    public class AuthResponse {
        private String accessToken;
        private Integer expiresIn;
        private Integer refreshExpiresIn;
        private String refreshToken;
        private String tokenType;
        private Integer notBeforePolicy;
        private String sessionState;
        private String scope;
    }
}