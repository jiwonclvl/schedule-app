package com.example.scheduleapp.member.dto.request;

import lombok.Getter;

@Getter
public class DeleteMemberRequestDto {

    private String password;

    public DeleteMemberRequestDto(String password) {
        this.password = password;
    }

}
