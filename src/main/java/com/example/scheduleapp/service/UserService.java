package com.example.scheduleapp.service;


import com.example.scheduleapp.dto.UpdateUserEmailRequestDto;
import com.example.scheduleapp.dto.UserRequestDto;
import com.example.scheduleapp.dto.UserResponseDto;

public interface UserService {

    //유저 생성
    public UserResponseDto createUser(String username, String email, String password);

    //특정 유저 조회
    public UserResponseDto findUser(Long id);

    //유저 이메일 수정
    public void updateUserEmail(Long id, String oldEmail, String newEmail);

    //유저 비밀번호 수정
    public void updateUserPassword(Long id, String oldPassword, String newPassword);

    //유저 삭제
    public void deleteUser(Long id, String password);
}
