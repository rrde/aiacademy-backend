package com.voice2learn.AIAcademy.auth.dto;

public class UserProfileResponse {

    private String email;

    public UserProfileResponse(String email) {
        this.email = email;
    }

    public String getEmail() {
        return email;
    }
}