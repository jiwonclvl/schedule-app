package com.example.scheduleapp.member.controller;


import com.example.scheduleapp.global.dto.SuccessResponseDto;
import com.example.scheduleapp.global.dto.SuccessWithDataResponseDto;
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
import static com.example.scheduleapp.global.dto.SuccessResponseDto.successOkResponse;

@Slf4j
@RestController
@RequestMapping("/members")
@RequiredArgsConstructor
public class MemberController {

    private final MemberServiceImpl memberService;

    @PostMapping("/signup")
    public ResponseEntity<SuccessWithDataResponseDto<MemberResponseDto>> createUser(
            @Validated @RequestBody MemberRequestDto dto
    ) {
        log.info("회원가입 API 호출");
        MemberResponseDto user = memberService.createUser(dto.getUsername(), dto.getEmail(), dto.getPassword());
        return SuccessWithDataResponseDto.successCreateResponse(HttpStatus.CREATED,"회원가입이 완료되었습니다.", user);
    }

    /*todo: 프로필 조회는 로그인 하지 않아도 조회할 수 있도록 수정해야 한다.*/
    @GetMapping("/{userId}")
    public ResponseEntity<SuccessWithDataResponseDto<MemberResponseDto>> findUser(@PathVariable Long userId) {
        log.info("특정 유저 조회 API 호출");
        MemberResponseDto userById = memberService.findUserById(userId);
        return SuccessWithDataResponseDto.successOkWithDataResponse(HttpStatus.OK, "유저 조회에 성공하였습니다.",userById);
    }

    /*세션 --> 검증 추가*/
    @PatchMapping("/email/{userId}")
    public ResponseEntity<SuccessResponseDto> updateUserEmail(
            @PathVariable Long userId,
            @Validated @RequestBody UpdateMemberEmailRequestDto dto
    ) {
        log.info("유저 이메일 수정 API 호출");
        memberService.updateUserEmail(userId, dto.getPassword(), dto.getNewEmail());
        return successOkResponse("이메일이 성공적으로 변경되었습니다.");
    }

    @PatchMapping("/password/{userId}")
    public ResponseEntity<SuccessResponseDto> updateUserPassword(
            @PathVariable Long userId,
            @Validated @RequestBody UpdateMemberPasswordRequestDto dto
    ) {
        log.info("유저 비밀번호 수정 API 호출");
        memberService.updateUserPassword(userId, dto.getOldPassword(), dto.getNewPassword());
        return successOkResponse("비밀번호가 성공적으로 변경되었습니다.");
    }

    /*todo: 시간이 된다면 -> soft delete | 되지 않는다면 -> cascade*/
    @DeleteMapping("/delete/{userId}")
    public ResponseEntity<SuccessResponseDto> deleteUser(
            @PathVariable Long userId,
            @Validated @RequestBody DeleteMemberRequestDto dto
    ) {
        log.info("유저 삭제 API 호출");
        memberService.deleteUser(userId, dto.getPassword());
        return successOkResponse("유저가 삭제 되었습니다.");
    }
}
