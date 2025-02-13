package com.example.scheduleapp.global.docs;

import com.example.scheduleapp.global.dto.SuccessResponseDto;
import com.example.scheduleapp.global.dto.SuccessWithDataResponseDto;
import com.example.scheduleapp.member.dto.request.DeleteMemberRequestDto;
import com.example.scheduleapp.member.dto.request.MemberRequestDto;
import com.example.scheduleapp.member.dto.request.UpdateMemberEmailRequestDto;
import com.example.scheduleapp.member.dto.request.UpdateMemberPasswordRequestDto;
import com.example.scheduleapp.member.dto.response.MemberResponseDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

@Tag(name = "사용자 CRUD 관리", description = "사용자의 회원가입 및 정보 수정, 삭제를 관리하는 API입니다.")
public interface MemberControllerDocs {
    @Operation(summary = "회원 가입", description = "유저명, 이메일, 비밀번호를 입력 받아 사용자를 등록합니다.")
    ResponseEntity<SuccessWithDataResponseDto<MemberResponseDto>> createUser(
            @Parameter(description = "회원 가입 요청 DTO") @Validated @RequestBody MemberRequestDto dto
    );

    @Operation(summary = "특정 유저 조회", description = "주어진 유저 ID를 통해 해당 유저의 정보를 조회하는 API입니다.")
    ResponseEntity<SuccessWithDataResponseDto<MemberResponseDto>> findUser(
            @Parameter(description = "조회할 유저의 유저 ID", required = true) @PathVariable Long userId
    );

    @Operation(summary = "유저 이메일 수정", description = "주어진 유저 ID에 대해 이메일을 수정하는 API입니다.")
    ResponseEntity<SuccessResponseDto> updateUserEmail(
            @Parameter(description = "수정할 유저의 ID", required = true) @PathVariable Long userId,
            @Parameter(description = "이메일 수정 요청 DTO") @Validated @RequestBody UpdateMemberEmailRequestDto dto,
            @Parameter(description = "요청을 보낸 사용자 세션 정보") HttpServletRequest request
    );

    @Operation(summary = "유저 비밀번호 수정", description = "주어진 유저 ID에 대해 비밀번호를 수정하는 API입니다.")
    ResponseEntity<SuccessResponseDto> updateUserPassword(
            @Parameter(description = "수정할 유저의 ID", required = true) @PathVariable Long userId,
            @Parameter(description = "비밀번호 수정 요청 DTO") @Validated @RequestBody UpdateMemberPasswordRequestDto dto,
            @Parameter(description = "요청을 보낸 사용자 세션 정보") HttpServletRequest request
    );

    @Operation(summary = "유저 삭제", description = "주어진 유저 ID에 대해 유저를 삭제하는 API입니다.")
    ResponseEntity<SuccessResponseDto> deleteUser(
            @Parameter(description = "삭제할 유저의 ID", required = true) @PathVariable Long userId,
            @Parameter(description = "삭제 요청 DTO") @Validated @RequestBody DeleteMemberRequestDto dto,
            @Parameter(description = "요청을 보낸 사용자 세션 정보") HttpServletRequest request
    );
}


