package com.example.scheduleapp.global.docs;

import com.example.scheduleapp.global.dto.SuccessResponseDto;
import com.example.scheduleapp.member.dto.request.LoginRequestDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;

@Tag(name = "사용자 로그인 및 로그 아웃 관리", description = "사용자의 로그인과 로그아웃을 관리하는 API입니다. ")
public interface AuthControllerDocs {
    @Operation(summary = "로그인", description = "이메일, 비밀번호를 입력받아 로그인을 진행합니다. ")
    public ResponseEntity<SuccessResponseDto> login(
            @Valid @RequestBody LoginRequestDto dto,
            HttpServletRequest request
    );

    @Operation(summary = "로그아웃", description = "사용자가 로그인 상태에서 로그아웃을 진행합니다.")
    public ResponseEntity<SuccessResponseDto> logout(
            HttpServletRequest request
    );
}
