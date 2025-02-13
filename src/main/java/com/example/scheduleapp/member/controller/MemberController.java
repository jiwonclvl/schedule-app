package com.example.scheduleapp.member.controller;


import com.example.scheduleapp.global.docs.MemberControllerDocs;
import com.example.scheduleapp.global.dto.SuccessResponseDto;
import com.example.scheduleapp.global.dto.SuccessWithDataResponseDto;
import com.example.scheduleapp.global.filter.SessionConst;
import com.example.scheduleapp.member.dto.request.DeleteMemberRequestDto;
import com.example.scheduleapp.member.dto.request.MemberRequestDto;
import com.example.scheduleapp.member.dto.request.UpdateMemberEmailRequestDto;
import com.example.scheduleapp.member.dto.request.UpdateMemberPasswordRequestDto;
import com.example.scheduleapp.member.dto.response.MemberResponseDto;
import com.example.scheduleapp.member.entity.Member;
import com.example.scheduleapp.member.service.MemberServiceImpl;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
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
public class MemberController implements MemberControllerDocs {

    private final MemberServiceImpl memberService;

    @PostMapping("/signup")
    public ResponseEntity<SuccessWithDataResponseDto<MemberResponseDto>> createUser(
            @Validated @RequestBody MemberRequestDto dto
    ) {
        log.info("회원가입 API 호출");

        MemberResponseDto user = memberService.createUser(dto.getUsername(), dto.getEmail(), dto.getPassword());
        return SuccessWithDataResponseDto.successCreateResponse(HttpStatus.CREATED,"회원가입이 완료되었습니다.", user);
    }

    @GetMapping("/{userId}")
    public ResponseEntity<SuccessWithDataResponseDto<MemberResponseDto>> findUser(@PathVariable Long userId) {
        log.info("특정 유저 조회 API 호출");
        MemberResponseDto userById = memberService.getUserById(userId);
        return SuccessWithDataResponseDto.successOkWithDataResponse(HttpStatus.OK, "유저 조회에 성공하였습니다.",userById);
    }

    @PatchMapping("/{userId}/email")
    public ResponseEntity<SuccessResponseDto> updateUserEmail(
            @PathVariable Long userId,
            @Validated @RequestBody UpdateMemberEmailRequestDto dto,
            HttpServletRequest request
    ) {
        log.info("유저 이메일 수정 API 호출");

        memberService.updateUserEmail(getLoginMemberId(request), userId, dto.getPassword(), dto.getNewEmail());
        return successOkResponse("이메일이 성공적으로 변경되었습니다.");
    }

    @PatchMapping("/{userId}/password")
    public ResponseEntity<SuccessResponseDto> updateUserPassword(
            @PathVariable Long userId,
            @Validated @RequestBody UpdateMemberPasswordRequestDto dto,
            HttpServletRequest request

    ) {
        log.info("유저 비밀번호 수정 API 호출");
        memberService.updateUserPassword(getLoginMemberId(request), userId, dto.getOldPassword(), dto.getNewPassword());
        return successOkResponse("비밀번호가 성공적으로 변경되었습니다.");
    }

    @DeleteMapping("/{userId}/delete")
    public ResponseEntity<SuccessResponseDto> deleteUser(
            @PathVariable Long userId,
            @Validated @RequestBody DeleteMemberRequestDto dto,
            HttpServletRequest request

    ) {
        log.info("유저 삭제 API 호출");
        memberService.deleteUser(getLoginMemberId(request), userId, dto.getPassword());
        return successOkResponse("유저가 삭제 되었습니다.");
    }

    private Long getLoginMemberId(HttpServletRequest request) {
        HttpSession session = request.getSession(false);
        Member loginMember = (Member) session.getAttribute(SessionConst.LOGIN_MEMBER);
        return loginMember.getId();
    }
}
