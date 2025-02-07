package com.example.scheduleapp.dto;

import lombok.Getter;

@Getter
public class MemberResponseDto {

    private final Long id;

    private final String username;

    private final String createdAt;

    private final String updateedAt;


    public MemberResponseDto(Long id, String username, String createdAt, String updateedAt) {
        this.id = id;
        this.username = username;
        this.createdAt = createdAt;
        this.updateedAt = updateedAt;
    }
}
