package com.example.scheduleapp.member.controller;

import com.example.scheduleapp.global.filter.SessionConst;
import com.example.scheduleapp.member.dto.request.LoginRequestDto;
import com.example.scheduleapp.member.entity.Member;
import com.example.scheduleapp.member.service.MemberServiceImpl;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;



@Slf4j
@RestController
@RequestMapping("/login")
@RequiredArgsConstructor
public class LoginController {

    private final MemberServiceImpl memberService;

    @PostMapping
    public ResponseEntity<Void> login(
            @Valid @RequestBody LoginRequestDto dto,
            HttpServletRequest request
    ) {
        log.info("로그인 API 호출");
        Member loginMember = memberService.findUserByEmailAndPassword(dto.getEmail(), dto.getPassword());

        /*세션에 값 저장*/
        request.getSession().setAttribute(SessionConst.LOGIN_MEMBER, loginMember);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
