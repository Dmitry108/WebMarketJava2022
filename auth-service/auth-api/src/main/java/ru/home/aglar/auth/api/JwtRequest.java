package ru.home.aglar.auth.api;

import io.swagger.v3.oas.annotations.media.Schema;

public class JwtRequest {
    @Schema(description = "Unique username", requiredMode = Schema.RequiredMode.REQUIRED)
    private String username;
    @Schema(description = "Password", requiredMode = Schema.RequiredMode.REQUIRED)
    private String password;

    public JwtRequest() {
    }

    public JwtRequest(String username, String password) {
        this.username = username;
        this.password = password;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}