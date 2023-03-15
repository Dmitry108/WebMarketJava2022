package ru.home.aglar.auth.api;

import io.swagger.v3.oas.annotations.media.Schema;

public class JwtResponse {
    @Schema(description = "Token", requiredMode = Schema.RequiredMode.REQUIRED)
    private String token;

    public JwtResponse() {
    }

    public JwtResponse(String token) {
        this.token = token;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
}