package com.example.scheduleapp.member.dto.response;

import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class MemberResponseDto {

    private final Long id;

    private final String username;

    private final LocalDateTime createdAt;

    private final LocalDateTime updatedAt;

    private MemberResponseDto(Long id, String username, LocalDateTime createdAt, LocalDateTime updatedAt) {
        this.id = id;
        this.username = username;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }

    public static MemberResponseDto memberDto(Long id, String username, LocalDateTime createdAt, LocalDateTime updatedAt) {
        return new MemberResponseDto (
                id,
                username,
                createdAt,
                updatedAt
        );
    }


}
