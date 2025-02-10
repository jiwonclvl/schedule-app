package com.example.scheduleapp.controller;


import com.example.scheduleapp.dto.request.DeleteMemberRequestDto;
import com.example.scheduleapp.dto.request.UpdateMemberEmailRequestDto;
import com.example.scheduleapp.dto.request.UpdateMemberPasswordRequestDto;
import com.example.scheduleapp.dto.response.MemberResponseDto;
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

    //특정 유저 조회
    @GetMapping("/{id}")
    public ResponseEntity<MemberResponseDto> findUser(@PathVariable Long id) {
        log.info("특정 유저 조회 API 호출");
        return new ResponseEntity<>(userService.findUserById(id), HttpStatus.OK);
    }

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

    //todo: 유저 삭제 시 모든 일정과 댓글 삭제
    //유저 삭제
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUser(
            @PathVariable Long id,
            @Validated @RequestBody DeleteMemberRequestDto dto
    ) {
        log.info("유저 삭제 API 호출");
        userService.deleteUser(id, dto.getPassword());

        return new ResponseEntity<>(HttpStatus.OK);
    }


}
