package com.example.scheduleapp.member.service;

import com.example.scheduleapp.global.config.PasswordEncoder;
import com.example.scheduleapp.global.exception.ErrorCode;
import com.example.scheduleapp.global.exception.custom.*;
import com.example.scheduleapp.member.dto.response.MemberResponseDto;
import com.example.scheduleapp.member.entity.Member;
import com.example.scheduleapp.member.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Optional;

import static com.example.scheduleapp.member.dto.response.MemberResponseDto.memberDto;

@Slf4j
@Service
@RequiredArgsConstructor
public class MemberServiceImpl{
    private final MemberRepository memberRepository;
    private final PasswordEncoder passwordEncoder;

    /*유저 등록 (회원 가입)*/
    @Transactional
    public MemberResponseDto createUser(String username, String email, String password) {

        Optional<Member> userByEmail = memberRepository.findUserByEmail(email);

        /*유저가 이미 존재하는 경우*/
        if(userByEmail.isPresent()) {
            throw new SignUpFailedException(ErrorCode.CONFLICT);
        }

        /*비밀번호 암호화*/
        String encodePassword = passwordEncoder.encode(password);
        Member user = new Member(username, email, encodePassword);

        Member savedUser = memberRepository.save(user);

        log.info("유저 저장 성공");

        //todo: 날짜 출력 변경하기
        return memberDto(
                savedUser.getId(),
                savedUser.getUsername(),
                localDateTimeFormat(savedUser.getCreatedAt()),
                localDateTimeFormat(savedUser.getUpdatedAt())
        );

    }

    /*유저 조회*/
    @Transactional(readOnly = true)
    public MemberResponseDto findUserById(Long id) {

        /*객체 조회*/
        Member userById = getUserById(id);

        log.info("특정 유저 조회 성공");

        return memberDto(
                userById.getId(),
                userById.getUsername(),
                localDateTimeFormat(userById.getCreatedAt()),
                localDateTimeFormat(userById.getUpdatedAt())
        );
    }

    /*비밀번호와 이메일을 통한 로그인*/
    @Transactional
    public Member findUserByEmailAndPassword(String email, String password) {

        Optional<Member> userByEmail = memberRepository.findUserByEmail(email);

        /*이메일 검증*/
        if(userByEmail.isEmpty()) {
            throw new PasswordException(ErrorCode.UNAUTHORIZED);
        }

        /*유저가 존재하는 경우 (즉, 이메일 검증이 통과된 경우)*/
        Member member = userByEmail.get();

        /*비밀번호 처리 메서드 호출*/
        validationPassword(password, member);

        log.info("로그인 성공");
        return member;
    }

    /*이메일 수정*/
    @Transactional
    public void updateUserEmail(Long id, String password, String newEmail) {
        /*객체 조회*/
        Member findUser = getUserById(id);

        /*비밀번호 처리 메서드 호출*/
        validationPassword(password, findUser);

        /*입력한 이메일이 기존 이메일과 동일한 경우*/
        if(findUser.getEmail().equals(newEmail)) {
            throw new EmailUnchangedException(ErrorCode.UNCHANGED_EMAIL);
        }

        findUser.updateEmail(newEmail);
    }

    /*비밀번호 수정*/
    @Transactional
    public void updateUserPassword(Long id, String oldPassword, String newPassword) {
        /*객체 조회*/
        Member findUser = getUserById(id);

        /*비밀번호 처리 메서드 호출*/
        validationPassword(oldPassword, findUser);

        /*입력한 비밀번호가 기존 비밀번호와 동일한 경우*/
        if(oldPassword.equals(newPassword)) {
            throw new PasswordUnchangedException(ErrorCode.UNCHANGED_PASSWORD);
        }

        //비밀번호 저장
        /*비밀번호 암호화*/
        String encodePassword = passwordEncoder.encode(newPassword);
        findUser.updatePassword(encodePassword);

    }

    /*todo: 유저 삭제 시 전체 삭제 구현하기*/
    @Transactional
    public void deleteUser(Long id, String password) {
        /*객체 조회*/
        Member findUser = getUserById(id);

        /*비밀번호 처리 메서드 호출*/
        validationPassword(password, findUser);
        memberRepository.delete(findUser);
    }

    private String localDateTimeFormat(LocalDateTime dateTime) {
        return dateTime.format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
    }

    public Member getUserById(Long id) {
        Optional<Member> findUserById = memberRepository.findUserById(id);

        /*조회 되는 객체가 없는 경우 NOT_FOUNT 예외처리*/
        if(findUserById.isEmpty()) {
            throw new EntityNotFoundException(ErrorCode.NOT_FOUND);
        }

        return findUserById.get();
    }

    private void validationPassword(String password, Member member) {
        //비밀번호 확인
        boolean passwordMatch = passwordEncoder.matches(password, member.getPassword());

        /*비밀번호 검증*/
        if(!passwordMatch) {
            throw new PasswordException(ErrorCode.UNAUTHORIZED);
        }
    }
}

