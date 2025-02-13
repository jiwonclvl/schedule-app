package com.example.scheduleapp.member.controller;

import com.example.scheduleapp.global.dto.SuccessResponseDto;
import com.example.scheduleapp.global.exception.ErrorCode;
import com.example.scheduleapp.global.exception.custom.NotLoggedInException;
import com.example.scheduleapp.global.filter.SessionConst;
import com.example.scheduleapp.member.dto.request.LoginRequestDto;
import com.example.scheduleapp.member.entity.Member;
import com.example.scheduleapp.member.service.MemberServiceImpl;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequiredArgsConstructor
public class AuthController {

    private final MemberServiceImpl memberService;

    @PostMapping("/login")
    public ResponseEntity<SuccessResponseDto> login(
            @Valid @RequestBody LoginRequestDto dto,
            HttpServletRequest request
    ) {
        log.info("로그인 API 호출");
        Member loginMember = memberService.findUserByEmailAndPassword(dto.getEmail(), dto.getPassword());

        HttpSession session = request.getSession();
        session.setAttribute(SessionConst.LOGIN_MEMBER, loginMember);

        return SuccessResponseDto.successOkResponse("로그인에 성공하였습니다.");
    }

    @PostMapping("/logout")
    public ResponseEntity<SuccessResponseDto> logout(
            HttpServletRequest request
    ) {
        HttpSession session = request.getSession(false);
        log.info("로그아웃 API 호출");

        if (session == null) {
            throw new NotLoggedInException(ErrorCode.NOT_LOGGED_IN);
        }

        session.invalidate();
        return SuccessResponseDto.successOkResponse("로그아웃에 성공하였습니다.");
    }
}
