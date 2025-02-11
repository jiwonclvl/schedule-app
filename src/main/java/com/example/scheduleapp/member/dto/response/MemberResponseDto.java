package com.example.scheduleapp.member.dto.response;

import lombok.Getter;

@Getter
public class MemberResponseDto {

    private final Long id;

    private final String username;

    private final String createdAt;

    private final String updatedAt;

    //private로 막은 후 static 메서드를 호출해서 MemberResponseDto빈허ㅣㄴ
    public MemberResponseDto(Long id, String username, String createdAt, String updatedAt) {
        this.id = id;
        this.username = username;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }


}
