package com.example.scheduleapp.dto;

import lombok.Getter;

@Getter
public class DeleteUserReqestDto {

    private String password;

    public DeleteUserReqestDto(String password) {
        this.password = password;
    }

}
