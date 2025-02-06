package com.example.scheduleapp.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;

@Getter
public class UpdateUserEmailRequestDto {

    //기존 이메일
    @NotBlank
    private final String oldEmail;

    //변경할 이메일
    @NotBlank
    @Email
    private final String newEmail;

    public UpdateUserEmailRequestDto(String oldEmail, String newEmail) {
        this.oldEmail = oldEmail;
        this.newEmail = newEmail;
    }
}
