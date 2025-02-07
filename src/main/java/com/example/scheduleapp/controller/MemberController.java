package com.example.scheduleapp.controller;


import com.example.scheduleapp.dto.*;
import com.example.scheduleapp.service.MemberServiceImpl;
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

    private final MemberServiceImpl userService;

    //유저 생성
    @PostMapping
    public ResponseEntity<MemberResponseDto> createUser(
            @Validated @RequestBody MemberRequestDto dto
    ) {
        log.info("유저 생성 API 호출");

        return new ResponseEntity<>(userService.createUser(dto.getUsername(),dto.getEmail(), dto.getPassword()), HttpStatus.OK);
    }

    //특정 유저 조회
    @GetMapping("/{id}")
    public ResponseEntity<MemberResponseDto> findUser(@PathVariable Long id) {
        log.info("특정 유저 조회 API 호출");
        return new ResponseEntity<>(userService.findUser(id), HttpStatus.OK);
    }

    //TODO 수정 부분 동일 따로 할 필요 없음
    //유저 이메일 수정
    @PatchMapping("/email/{id}")
    public ResponseEntity<Void> updateUserEmail(
            @PathVariable Long id,
            @Validated @RequestBody UpdateMemberEmailRequestDto dto
    ) {
        log.info("유저 이메일 수정 API 호출");
        userService.updateUserEmail(id, dto.getPassword(), dto.getNewEmail());
        return new ResponseEntity<>(HttpStatus.OK);
    }

    //유저 비밀번호 수정
    @PatchMapping("/password/{id}")
    public ResponseEntity<Void> updateUserPassword(
            @PathVariable Long id,
            @Validated @RequestBody UpdateMemberPasswordRequestDto dto
    ) {
        log.info("유저 비밀번호 수정 API 호출");
        userService.updateUserPassword(id, dto.getOldPassword(), dto.getNewPassword());
        return new ResponseEntity<>(HttpStatus.OK);
    }

    //유저 삭제
    //TODO: body말고 param으로
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUser(
            @PathVariable Long id,
            @Validated @RequestBody DeleteMemberReqestDto dto
    ) {
        log.info("유저 삭제 API 호출");
        userService.deleteUser(id, dto.getPassword());

        return new ResponseEntity<>(HttpStatus.OK);
    }


}
