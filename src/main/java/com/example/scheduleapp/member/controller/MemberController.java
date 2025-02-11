package com.example.scheduleapp.member.controller;


import com.example.scheduleapp.global.SuccessResponseDto;
import com.example.scheduleapp.member.dto.request.DeleteMemberRequestDto;
import com.example.scheduleapp.member.dto.request.MemberRequestDto;
import com.example.scheduleapp.member.dto.request.UpdateMemberEmailRequestDto;
import com.example.scheduleapp.member.dto.request.UpdateMemberPasswordRequestDto;
import com.example.scheduleapp.member.dto.response.MemberResponseDto;
import com.example.scheduleapp.member.service.MemberServiceImpl;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping("/members")
@RequiredArgsConstructor
public class MemberController {

    private final MemberServiceImpl memberService;

    @PostMapping("/signup")
    public ResponseEntity<MemberResponseDto> createUser(
            @Validated @RequestBody MemberRequestDto dto
    ) {
        log.info("회원가입 API 호출");
        return new ResponseEntity<>(memberService.createUser(dto.getUsername(),dto.getEmail(), dto.getPassword()), HttpStatus.OK);
    }

    @GetMapping("/{userId}")
    public ResponseEntity<MemberResponseDto> findUser(@PathVariable Long userId) {
        log.info("특정 유저 조회 API 호출");
        return new ResponseEntity<>(memberService.findUserById(userId), HttpStatus.OK);
    }

    @PatchMapping("/email/{userId}")
    public ResponseEntity<SuccessResponseDto> updateUserEmail(
            @PathVariable Long userId,
            @Validated @RequestBody UpdateMemberEmailRequestDto dto
    ) {
        log.info("유저 이메일 수정 API 호출");
        String message = memberService.updateUserEmail(userId, dto.getPassword(), dto.getNewEmail());
        return SuccessResponseDto.successResponse(message);
    }

    @PatchMapping("/password/{userId}")
    public ResponseEntity<SuccessResponseDto> updateUserPassword(
            @PathVariable Long userId,
            @Validated @RequestBody UpdateMemberPasswordRequestDto dto
    ) {
        log.info("유저 비밀번호 수정 API 호출");
        String message = memberService.updateUserPassword(userId, dto.getOldPassword(), dto.getNewPassword());
        return SuccessResponseDto.successResponse(message);
    }

    @DeleteMapping("/delete/{userId}")
    public ResponseEntity<SuccessResponseDto> deleteUser(
            @PathVariable Long userId,
            @Validated @RequestBody DeleteMemberRequestDto dto
    ) {
        log.info("유저 삭제 API 호출");
        String message = memberService.deleteUser(userId, dto.getPassword());

        return SuccessResponseDto.successResponse(message);
    }
}
