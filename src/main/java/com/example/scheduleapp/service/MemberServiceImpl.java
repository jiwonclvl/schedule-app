package com.example.scheduleapp.service;

import com.example.scheduleapp.dto.MemberResponseDto;
import com.example.scheduleapp.entity.Member;
import com.example.scheduleapp.repository.MemberRepository;
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
public class MemberServiceImpl implements MemberService {
    private final MemberRepository userRepository;

    @Override
    @Transactional
    public MemberResponseDto createUser(String username, String email, String password) {
        Member user = new Member(username, email, password);

        Member savedUser = userRepository.save(user);

        log.info("유저 저장 성공");

        return new MemberResponseDto(
                savedUser.getId(),
                savedUser.getUsername(),
                localDateTimeFormat(savedUser.getCreatedAt()),
                localDateTimeFormat(savedUser.getUpdatedAt())
        );
    }

    @Override
    public MemberResponseDto findUserById(Long id) {
        Member findUser = userRepository.findUserByIdOrElseThrow(id);
        log.info("특정 유저 조회 성공");

        return new MemberResponseDto(
                findUser.getId(),
                findUser.getUsername(),
                localDateTimeFormat(findUser.getCreatedAt()),
                localDateTimeFormat(findUser.getUpdatedAt())
        );
    }

    @Override
    @Transactional
    public Long findUserByEmailAndPassword(String email, String password) {
        Member findUser = userRepository.findUserByEmailOrElseThrow(email);

        if (findUser.getEmail().equals(email) && findUser.getPassword().equals(password)) {
            log.info("로그인 성공");
            return findUser.getId();
        }

        throw new ResponseStatusException(HttpStatus.UNAUTHORIZED);

    }

    @Override
    @Transactional
    public void updateUserEmail(Long id, String password, String newEmail) {
        Member findUser = userRepository.findUserByIdOrElseThrow(id);
        log.info("유저 조회 성공");

        //TODO: 변경할 이메일이 기존 이메일과 같은 경우에는 --> 200대는 되지만 기존과 같다고 메세지 띄우고 싶음
        if(!findUser.getPassword().equals(password)) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "비밀번호가 일치하지 않습니다.");
        }

        findUser.updateEmail(newEmail);
        log.info("유저 이메일 수정 성공");
    }

    @Override
    @Transactional
    public void updateUserPassword(Long id, String oldPassword, String newPassword) {
        Member findUser = userRepository.findUserByIdOrElseThrow(id);
        log.info("유저 조회 성공");

        if(!findUser.getPassword().equals(oldPassword)) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "비밀번호가 일치하지 않습니다.");
        }

        findUser.updatePassword(newPassword);
        log.info("유저 비밀번호 수정 성공");
    }

    @Override
    @Transactional
    public void deleteUser(Long id, String password) {
        Member findUser = userRepository.findUserByIdOrElseThrow(id);
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

