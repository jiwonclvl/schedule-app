package com.example.scheduleapp.dto;

import lombok.Getter;

@Getter
public class DeleteMemberReqestDto {

    private String password;

    public DeleteMemberReqestDto(String password) {
        this.password = password;
    }

}
