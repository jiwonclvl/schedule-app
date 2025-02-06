package com.example.scheduleapp.service;

import com.example.scheduleapp.dto.UserResponseDto;
import com.example.scheduleapp.entity.User;
import com.example.scheduleapp.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;

    @Override
    @Transactional
    public UserResponseDto createUser(String username, String email, String password) {
        User user = new User(username, email, password);

        User savedUser = userRepository.save(user);

        log.info("유저 저장 성공");

        return new UserResponseDto(
                savedUser.getUsername(),
                localDateTimeFormat(savedUser.getCreatedAt()),
                localDateTimeFormat(savedUser.getUpdatedAt())
        );
    }

    @Override
    public UserResponseDto findUser(Long id) {
        User user = userRepository.findUserByIdOrElseThrow(id);
        log.info("특정 유저 조회 성공");

        return new UserResponseDto(
                user.getUsername(),
                localDateTimeFormat(user.getCreatedAt()),
                localDateTimeFormat(user.getUpdatedAt())
        );
    }

    @Override
    @Transactional
    public void updateUserEmail(Long id, String oldEmail, String newEmail) {
        User findUser = userRepository.findUserByIdOrElseThrow(id);
        log.info("유저 조회 성공");

        //TODO: 이메일 일치로 수정하는 것보다 비밀번호 일치시 수정하는 것이 맞는 것 같음
        //TODO: 변경할 이메일이 기존 이메일과 같은 경우에는 --> 200대는 되지만 기존과 같다고 메세지 띄우고 싶음
        if(!findUser.getEmail().equals(oldEmail)) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "이메일이 일치하지 않습니다. ");
        }

        findUser.updateEmail(newEmail);
        log.info("유저 이메일 수정 성공");
    }

    @Override
    @Transactional
    public void updateUserPassword(Long id, String oldPassword, String newPassword) {
        User findUser = userRepository.findUserByIdOrElseThrow(id);
        log.info("유저 조회 성공");

        if(!findUser.getPassword().equals(oldPassword)) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "비밀번호가 일치하지 않습니다.");
        }

        findUser.updateEmail(newPassword);
        log.info("유저 비밀번호 수정 성공");
    }

    @Override
    @Transactional
    public void deleteUser(Long id, String password) {
        User findUser = userRepository.findUserByIdOrElseThrow(id);
        log.info("유저 조회 성공");

        if(!findUser.getPassword().equals(password)) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "비밀번호가 일치하지 않습니다.");
        }

        userRepository.delete(findUser);
        log.info("유저 삭제 성공");
    }

    private String localDateTimeFormat(LocalDateTime dateTime) {
        return dateTime.format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
    }
}

