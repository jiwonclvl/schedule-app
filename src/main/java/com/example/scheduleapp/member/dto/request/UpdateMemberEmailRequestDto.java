package com.example.scheduleapp.member.dto.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;

@Getter
public class UpdateMemberEmailRequestDto {

    @NotBlank (message = "비밀번호를 입력해주세요")
    private final String password;

    //변경할 이메일
    @NotBlank (message = "이메일을 입력해주세요")
    @Email (message = "이메일 형식이 맞지 않습니다.")
    private final String newEmail;

    public UpdateMemberEmailRequestDto(String password, String newEmail) {
        this.password = password;
        this.newEmail = newEmail;
    }
}
