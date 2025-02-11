package com.example.scheduleapp.member.dto.request;

import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Getter;


@Getter
@AllArgsConstructor
public class MemberRequestDto {

    @NotBlank (message = "이름을 입력해주세요")
    private final String username;

    @NotBlank (message = "이메일을 입력해주세요")
    @Email (message = "이메일 형식이 맞지 않습니다.")
    private final String email;

    @Pattern(
            regexp = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[@$!%*?&#])[A-Za-z\\d@$!%*?&#]{8,}$",
            message = "비밀번호 형식이 올바르지 않습니다. 8자 이상, 대소문자 포함, 숫자 및 특수문자(@$!%*?&#)가 포함되어야 합니다."
    )
    @NotBlank (message = "비밀번호를 입력해주세요")
    private final String password;

}
