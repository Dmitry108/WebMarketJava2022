package ru.home.aglar.market.core.api;

public class ProfileDto {
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