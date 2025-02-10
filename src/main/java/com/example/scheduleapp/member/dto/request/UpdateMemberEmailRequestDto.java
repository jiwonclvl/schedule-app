package com.example.scheduleapp.member.dto.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;

@Getter
public class UpdateMemberEmailRequestDto {

    @NotBlank
    private final String password;

    //변경할 이메일
    @NotBlank
    @Email
    private final String newEmail;

    public UpdateMemberEmailRequestDto(String password, String newEmail) {
        this.password = password;
        this.newEmail = newEmail;
    }
}
