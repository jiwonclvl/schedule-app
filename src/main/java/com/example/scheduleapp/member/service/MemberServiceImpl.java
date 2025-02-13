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
import java.util.Optional;

import static com.example.scheduleapp.member.dto.response.MemberResponseDto.memberDto;

@Slf4j
@Service
@RequiredArgsConstructor
public class MemberServiceImpl{

    private final MemberRepository memberRepository;
    private final PasswordEncoder passwordEncoder;

    /**
     * 사용자를 생성하고 저장한다.
     *
     * @param username  사용자의 이름
     * @param email     사용자의 이메일
     * @param password  사용자의 비밀번호 (암호화됨)
     * @return          생성된 사용자의 정보를 담은 MemberResponseDto (유저 ID, 유저명, 등록일, 수정일)
     * @throws SignUpFailedException 이메일이 이미 존재하는 경우 발생
     */
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

        return memberDto(
                savedUser.getId(),
                savedUser.getUsername(),
                savedUser.getCreatedAt(),
                savedUser.getUpdatedAt()
        );

    }

    /**
     * 사용자 ID를 기반으로 특정 사용자를 조회한다.
     *
     * @param userId 조회할 사용자의 ID
     * @return       조회된 사용자의 정보를 담은 MemberResponseDto (유저 ID, 유저명, 등록일, 수정일)
     * @throws EntityNotFoundException 해당 ID의 사용자가 존재하지 않는 경우 발생
     */
    @Transactional(readOnly = true)
    public MemberResponseDto getUserById(Long userId) {

        Member userById = findUserById(userId);

        log.info("특정 유저 조회 성공");

        return memberDto(
                userById.getId(),
                userById.getUsername(),
                userById.getCreatedAt(),
                userById.getUpdatedAt()
        );
    }

    /**
     * 이메일과 비밀번호를 사용하여 사용자를 인증한다.
     *
     * @param email    사용자의 이메일
     * @param password 입력된 비밀번호
     * @return         인증된 사용자 객체 Member (사용자 ID, 유저명, 이메일, 비밀번호, 일정 리스트)
     * @throws NoSuchEmailException 입력한 이메일을 가지는 유저가 없는 경우 발생
     * @throws MismatchedPasswordException 비밀번호가 일치하지 않는 경우 발생
     */
    @Transactional
    public Member findUserByEmailAndPassword(String email, String password) {

        Optional<Member> userByEmail = memberRepository.findUserByEmail(email);

        /*해당 이메일을 가지는 유저가 없는 경우*/
        if(userByEmail.isEmpty()) {
            throw new NoSuchEmailException(ErrorCode.UNAUTHORIZED);
        }

        Member member = userByEmail.get();

        /*비밀번호 검증 메서드 호출*/
        validationPassword(password, member);

        log.info("로그인 성공");
        return member;
    }

    /**
     * 사용자 이메일을 수정한다.
     *
     * 주어진 사용자 ID와 로그인된 사용자의 ID가 일치하는지 확인하고,
     * 비밀번호가 맞는지 검증한 후 이메일을 수정한다.
     * 입력된 새 이메일이 기존 이메일과 동일한 경우에는 예외를 발생시킨다.
     *
     * @param loginMember 로그인된 사용자의 ID
     * @param userId 이메일을 수정할 대상 사용자의 ID
     * @param password 사용자의 현재 비밀번호
     * @param newEmail 사용자가 변경하려는 새로운 이메일
     * @throws EntityNotFoundException  해당 userId의 사용자가 존재하지 않는 경우 발생
     * @throws ForbiddenException 로그인된 사용자가 다른 사용자의 이메일을 변경하려고 할 경우 발생
     * @throws MismatchedPasswordException 비밀번호 검증에 실패할 경우 발생
     * @throws EmailUnchangedException 새 이메일이 기존 이메일과 동일할 경우 발생
     */
    @Transactional
    public void updateUserEmail(Long loginMember, Long userId, String password, String newEmail) {

        Member findUser = findUserById(userId);

        /*유저 권한 검증 메서드 호출*/
        validationUser(loginMember,findUser.getId());

        /*비밀번호 검증 메서드 호출*/
        validationPassword(password, findUser);

        /*입력한 이메일이 기존 이메일과 동일한 경우*/
        if(findUser.getEmail().equals(newEmail)) {
            throw new EmailUnchangedException(ErrorCode.UNCHANGED_EMAIL);
        }

        findUser.updateEmail(newEmail);
    }

    /**
     * 사용자 비밀번호를 수정한다.
     *
     * 주어진 사용자 ID와 로그인된 사용자의 ID가 일치하는지 확인하고,
     * 기존 비밀번호가 맞는지 검증한 후 새로운 비밀번호로 변경한다.
     * 입력된 새 비밀번호가 기존 비밀번호와 동일한 경우에는 예외를 발생시킨다.
     *
     * @param loginMember 로그인된 사용자의 ID
     * @param userId 비밀번호를 수정할 대상 사용자의 ID
     * @param oldPassword 사용자의 현재 비밀번호
     * @param newPassword 사용자가 변경하려는 새로운 비밀번호
     * @throws EntityNotFoundException 해당 id의 사용자가 존재하지 않는 경우 발생
     * @throws ForbiddenException 로그인된 사용자가 다른 사용자의 비밀번호를 변경하려고 할 경우 발생
     * @throws MismatchedPasswordException 비밀번호 검증에 실패할 경우 발생
     * @throws PasswordUnchangedException 새 비밀번호가 기존 비밀번호와 동일할 경우 발생
     */
    @Transactional
    public void updateUserPassword(Long loginMember, Long userId, String oldPassword, String newPassword) {

        Member findUser = findUserById(userId);

        /*유저 권한 검증 메서드 호출*/
        validationUser(loginMember,findUser.getId());

        /*비밀번호 검증 메서드 호출*/
        validationPassword(oldPassword, findUser);

        /*입력한 비밀번호가 기존 비밀번호와 동일한 경우*/
        if(oldPassword.equals(newPassword)) {
            throw new PasswordUnchangedException(ErrorCode.UNCHANGED_PASSWORD);
        }

        /*비밀번호 암호화 후 저장*/
        String encodePassword = passwordEncoder.encode(newPassword);
        findUser.updatePassword(encodePassword);

    }

    /**
     * 사용자를 삭제한다.
     *
     * 주어진 사용자 ID와 로그인된 사용자의 ID가 일치하는지 확인하고,
     * 비밀번호가 맞는지 검증한 후 해당 사용자를 삭제한다.
     *
     * @param loginMember 로그인된 사용자의 ID
     * @param userId 삭제할 대상 사용자의 ID
     * @param password 사용자의 현재 비밀번호
     * @throws EntityNotFoundException 해당 id의 사용자가 존재하지 않는 경우 발생
     * @throws ForbiddenException 로그인된 사용자가 다른 사용자의 정보를 삭제하려 할 경우 발생
     * @throws MismatchedPasswordException 비밀번호 검증에 실패할 경우 발생
     */
    @Transactional
    public void deleteUser(Long loginMember, Long userId, String password) {

        Member findUser = findUserById(userId);

        /*유저 권한 검증 메서드 호출*/
        validationUser(loginMember,findUser.getId());

        /*비밀번호 검증 메서드 호출*/
        validationPassword(password, findUser);
        memberRepository.delete(findUser);
    }

    /**
     * 사용자 ID로 사용자를 조회하는 메서드이다.
     *
     * @param id 조회할 사용자의 ID
     * @return 조회된 사용자 객체
     * @throws EntityNotFoundException 해당 id의 사용자가 존재하지 않는 경우 발생
     */
    public Member findUserById(Long id) {
        Optional<Member> findUserById = memberRepository.getUserById(id);

        if(findUserById.isEmpty()) {
            throw new EntityNotFoundException(ErrorCode.NOT_FOUND);
        }

        return findUserById.get();
    }

    /**
     * 비밀번호를 검증하는 메서드이다.
     *
     * @param password 사용자가 입력한 현재 비밀번호
     * @param member 비밀번호를 검증할 대상 사용자 객체
     * @throws MismatchedPasswordException 비밀번호 검증에 실패할 경우 발생
     */
    private void validationPassword(String password, Member member) {
        boolean passwordMatch = passwordEncoder.matches(password, member.getPassword());

        /*비밀번호 검증*/
        if(!passwordMatch) {
            throw new MismatchedPasswordException(ErrorCode.UNAUTHORIZED);
        }
    }

    /**
     * 로그인된 사용자가 다른 사용자의 데이터를 수정할 수 없도록 하는 권한 검증 메서드이다.
     *
     * @param loginMember 로그인된 사용자의 ID
     * @param userId 대상 사용자의 ID
     * @throws ForbiddenException 로그인된 사용자가 다른 사용자의 데이터를 수정하려 할 경우 발생
     */
    private void validationUser(Long loginMember, Long userId) {
        if (!loginMember.equals(userId)) {
            throw new ForbiddenException(ErrorCode.CANNOT_UPDATE_OTHERS_DATA);
        }
    }
}

