package com.example.scheduleapp.member.dto.request;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;

@Getter
public class DeleteMemberRequestDto {

    private final String password;

    @JsonCreator
    public DeleteMemberRequestDto(@JsonProperty("password") String password) {
        this.password = password;
    }
}
