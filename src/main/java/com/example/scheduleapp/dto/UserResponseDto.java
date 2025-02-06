package com.example.scheduleapp.dto;

import lombok.Getter;

@Getter
public class UserResponseDto {

    private final String username;

    private final String createdAt;

    private final String updateedAt;


    public UserResponseDto(String username, String createdAt, String updateedAt) {
        this.username = username;
        this.createdAt = createdAt;
        this.updateedAt = updateedAt;
    }
}
