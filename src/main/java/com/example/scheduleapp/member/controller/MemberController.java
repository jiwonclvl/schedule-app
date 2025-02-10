package com.example.scheduleapp.member.controller;


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

    //todo: 이미 존재하는 이메일인 경우 메세지 출력하기
    @PostMapping("/signup")
    public ResponseEntity<MemberResponseDto> createUser(
            @Validated @RequestBody MemberRequestDto dto
    ) {
        log.info("회원가입 API 호출");
        return new ResponseEntity<>(memberService.createUser(dto.getUsername(),dto.getEmail(), dto.getPassword()), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<MemberResponseDto> findUser(@PathVariable Long id) {
        log.info("특정 유저 조회 API 호출");
        return new ResponseEntity<>(memberService.findUserById(id), HttpStatus.OK);
    }

    @PatchMapping("/email/{id}")
    public ResponseEntity<Void> updateUserEmail(
            @PathVariable Long id,
            @Validated @RequestBody UpdateMemberEmailRequestDto dto
    ) {
        log.info("유저 이메일 수정 API 호출");
        memberService.updateUserEmail(id, dto.getPassword(), dto.getNewEmail());
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PatchMapping("/password/{id}")
    public ResponseEntity<Void> updateUserPassword(
            @PathVariable Long id,
            @Validated @RequestBody UpdateMemberPasswordRequestDto dto
    ) {
        log.info("유저 비밀번호 수정 API 호출");
        memberService.updateUserPassword(id, dto.getOldPassword(), dto.getNewPassword());
        return new ResponseEntity<>(HttpStatus.OK);
    }

    //todo: 유저 삭제 시 모든 일정과 댓글 --> 남겨두기
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUser(
            @PathVariable Long id,
            @Validated @RequestBody DeleteMemberRequestDto dto
    ) {
        log.info("유저 삭제 API 호출");
        memberService.deleteUser(id, dto.getPassword());

        return new ResponseEntity<>(HttpStatus.OK);
    }


}
