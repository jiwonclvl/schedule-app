package com.example.scheduleapp.member.dto.request;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;

@Getter
public class UpdateMemberPasswordRequestDto {

    @NotBlank
    private String oldPassword;

    @NotBlank
    private String newPassword;

    public UpdateMemberPasswordRequestDto(String oldPassword, String newPassword) {
        this.oldPassword = oldPassword;
        this.newPassword = newPassword;
    }


}
