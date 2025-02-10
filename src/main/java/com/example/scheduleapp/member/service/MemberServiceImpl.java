package com.example.scheduleapp.member.service;

import com.example.scheduleapp.global.config.PasswordEncoder;
import com.example.scheduleapp.member.dto.response.MemberResponseDto;
import com.example.scheduleapp.member.entity.Member;
import com.example.scheduleapp.member.repository.MemberRepository;
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
    private final PasswordEncoder passwordEncoder;

    @Override
    @Transactional
    public MemberResponseDto createUser(String username, String email, String password) {

        //비밀번호 암호화
        String encodePassword = passwordEncoder.encode(password);
        Member user = new Member(username, email, encodePassword);

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

        //비밀번호 확인
        boolean passwordMatch = passwordEncoder.matches(password, findUser.getPassword());

        if (findUser.getEmail().equals(email) && passwordMatch) {
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

        //비밀번호 확인
        boolean passwordMatch = passwordEncoder.matches(password, findUser.getPassword());

        if(!passwordMatch) {
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

        //비밀번호 확인
        boolean passwordMatch = passwordEncoder.matches(oldPassword, findUser.getPassword());

        if(!passwordMatch) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "비밀번호가 일치하지 않습니다.");
        }

        //비밀번호 암호화
        String encodePassword = passwordEncoder.encode(newPassword);
        findUser.updatePassword(encodePassword);
        log.info("유저 비밀번호 수정 성공");
    }

    @Override
    @Transactional
    public void deleteUser(Long id, String password) {
        Member findUser = userRepository.findUserByIdOrElseThrow(id);
        log.info("유저 조회 성공");

        //비밀번호 확인
        boolean passwordMatch = passwordEncoder.matches(password, findUser.getPassword());

        if(!passwordMatch) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "비밀번호가 일치하지 않습니다.");
        }

        userRepository.delete(findUser);
        log.info("유저 삭제 성공");
    }

    private String localDateTimeFormat(LocalDateTime dateTime) {
        return dateTime.format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
    }
}

