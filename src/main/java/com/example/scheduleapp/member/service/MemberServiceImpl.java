package com.example.scheduleapp.member.service;

import com.example.scheduleapp.global.config.PasswordEncoder;
import com.example.scheduleapp.global.exception.ErrorCode;
import com.example.scheduleapp.global.exception.custom.LoginFailedException;
import com.example.scheduleapp.global.exception.custom.SignUpFailedException;
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
import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class MemberServiceImpl{
    private final MemberRepository memberRepository;
    private final PasswordEncoder passwordEncoder;

    @Transactional
    public MemberResponseDto createUser(String username, String email, String password) {

        Optional<Member> userByEmail = memberRepository.findUserByEmail(email);

        /*유저가 이미 존재하는 경우*/
        if(userByEmail.isPresent()) {
            throw new SignUpFailedException(ErrorCode.CONFLICT);
        }

        //비밀번호 암호화
        String encodePassword = passwordEncoder.encode(password);
        Member user = new Member(username, email, encodePassword);

        Member savedUser = memberRepository.save(user);

        log.info("유저 저장 성공");

        return new MemberResponseDto(
                savedUser.getId(),
                savedUser.getUsername(),
                localDateTimeFormat(savedUser.getCreatedAt()),
                localDateTimeFormat(savedUser.getUpdatedAt())
        );
    }

    public MemberResponseDto findUserById(Long id) {
        Member findUser = memberRepository.findUserByIdOrElseThrow(id);

        /*조회 되는 유저가 없는 경우 NOT_FOUNT 예외처리*/
        if(findUser == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }

        log.info("특정 유저 조회 성공");

        return new MemberResponseDto(
                findUser.getId(),
                findUser.getUsername(),
                localDateTimeFormat(findUser.getCreatedAt()),
                localDateTimeFormat(findUser.getUpdatedAt())
        );
    }

    @Transactional
    public Long findUserByEmailAndPassword(String email, String password) {

        Optional<Member> userByEmail = memberRepository.findUserByEmail(email);

        /*이메일 검증*/
        if(userByEmail.isEmpty()) {
            throw new LoginFailedException(ErrorCode.UNAUTHORIZED);
        }

        /*유저가 존재하는 경우 (즉, 이메일 검증이 통과된 경우)*/
        Member member = userByEmail.get();

        /*비밀번호 검증*/
        boolean passwordMatch = passwordEncoder.matches(password, member.getPassword());

        if(!passwordMatch) {
            throw new LoginFailedException(ErrorCode.UNAUTHORIZED);
        }

        log.info("로그인 성공");
        return member.getId();
    }

    @Transactional
    public void updateUserEmail(Long id, String password, String newEmail) {
        Member findUser = memberRepository.findUserByIdOrElseThrow(id);

        //비밀번호 확인
        boolean passwordMatch = passwordEncoder.matches(password, findUser.getPassword());

        if(!passwordMatch) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "비밀번호가 일치하지 않습니다.");
        }

        findUser.updateEmail(newEmail);
        log.info("유저 이메일 수정 성공");
    }

    @Transactional
    public void updateUserPassword(Long id, String oldPassword, String newPassword) {
        Member findUser = memberRepository.findUserByIdOrElseThrow(id);
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

    @Transactional
    public void deleteUser(Long id, String password) {
        Member findUser = memberRepository.findUserByIdOrElseThrow(id);

        //비밀번호 확인
        boolean passwordMatch = passwordEncoder.matches(password, findUser.getPassword());

        if(!passwordMatch) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "비밀번호가 일치하지 않습니다.");
        }

        memberRepository.delete(findUser);
        log.info("유저 삭제 성공");
    }

    private String localDateTimeFormat(LocalDateTime dateTime) {
        return dateTime.format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
    }
}

