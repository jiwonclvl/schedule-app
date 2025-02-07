package com.example.scheduleapp.service;


import com.example.scheduleapp.dto.MemberResponseDto;

public interface MemberService {

    //유저 생성
    public MemberResponseDto createUser(String username, String email, String password);

    //특정 유저 조회
    public MemberResponseDto findUser(Long id);

    //유저 이메일 수정
    public void updateUserEmail(Long id,String password, String newEmail);

    //유저 비밀번호 수정
    public void updateUserPassword(Long id, String oldPassword, String newPassword);

    //유저 삭제
    public void deleteUser(Long id, String password);
}
