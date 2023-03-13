package ru.home.aglar.market.core.api;

import io.swagger.v3.oas.annotations.media.Schema;

public class ProfileDto {
    @Schema(description = "Unique username", requiredMode = Schema.RequiredMode.REQUIRED)
    private String username;
//    private String email;


    public ProfileDto() {
    }

    public ProfileDto(String username) {
        this.username = username;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }
}