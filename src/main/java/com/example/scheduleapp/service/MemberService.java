package com.example.scheduleapp.service;


import com.example.scheduleapp.dto.response.MemberResponseDto;

public interface MemberService {

    //유저 생성
    public MemberResponseDto createUser(String username, String email, String password);

    //특정 유저 조회
    public MemberResponseDto findUserById(Long id);

    //이메일과 비밀번호로 유저 조회 (로그인)
    public Long findUserByEmailAndPassword(String email, String password);

    //유저 이메일 수정
    public void updateUserEmail(Long id,String password, String newEmail);

    //유저 비밀번호 수정
    public void updateUserPassword(Long id, String oldPassword, String newPassword);

    //유저 삭제
    public void deleteUser(Long id, String password);
}
