package com.example.scheduleapp.member.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.Getter;

@Getter
public class UpdateMemberPasswordRequestDto {

    @Pattern(
            regexp = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[@$!%*?&#])[A-Za-z\\d@$!%*?&#]{8,}$",
            message = "비밀번호 형식이 올바르지 않습니다. 8자 이상, 대소문자 포함, 숫자 및 특수문자(@$!%*?&#)가 포함되어야 합니다."
    )
    @NotBlank (message = "비밀번호를 입력해주세요")
    private String oldPassword;

    @Pattern(
            regexp = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[@$!%*?&#])[A-Za-z\\d@$!%*?&#]{8,}$",
            message = "비밀번호 형식이 올바르지 않습니다. 8자 이상, 대소문자 포함, 숫자 및 특수문자(@$!%*?&#)가 포함되어야 합니다."
    )
    @NotBlank (message = "비밀번호를 입력해주세요")
    private String newPassword;

    public UpdateMemberPasswordRequestDto(String oldPassword, String newPassword) {
        this.oldPassword = oldPassword;
        this.newPassword = newPassword;
    }


}
