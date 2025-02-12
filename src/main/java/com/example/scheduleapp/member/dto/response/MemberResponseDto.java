package com.example.scheduleapp.member.dto.response;

import lombok.Getter;

@Getter
public class MemberResponseDto {

    private final Long id;

    private final String username;

    private final String createdAt;

    private final String updatedAt;

    private MemberResponseDto(Long id, String username, String createdAt, String updatedAt) {
        this.id = id;
        this.username = username;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }

    public static MemberResponseDto memberDto(Long id, String username, String createdAt, String updatedAt) {
        return new MemberResponseDto (
                id,
                username,
                createdAt,
                updatedAt
        );
    }


}
