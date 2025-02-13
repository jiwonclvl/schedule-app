package com.example.scheduleapp.global.exception;

import com.example.scheduleapp.comment.controller.CommentController;
import com.example.scheduleapp.global.exception.custom.*;
import com.example.scheduleapp.global.dto.ErrorResponseDto;
import com.example.scheduleapp.global.dto.ValidationErrorResponseDto;
import com.example.scheduleapp.member.controller.AuthController;
import com.example.scheduleapp.member.controller.MemberController;
import com.example.scheduleapp.schedule.controller.ScheduleController;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import java.util.List;


@Slf4j
@RestControllerAdvice
public class CustomExceptionHandler {

    /*로그인 기능 예외처리*/
    @ExceptionHandler(MismatchedPasswordException.class)
    public ResponseEntity<ErrorResponseDto> handlePasswordException(MismatchedPasswordException exception){

        log.error("HttpStatus.UNAUTHORIZED 예외 발생");

        ErrorCode errorCode = exception.getErrorCode();
        return ErrorResponseDto.errorResponse(errorCode.getErrorCode(), exception.getMessage());
    }

    /*회원 가입 예외 처리*/
    @ExceptionHandler(SignUpFailedException.class)
    public ResponseEntity<ErrorResponseDto> handlePasswordException(SignUpFailedException exception){

        log.error("HttpStatus.CONFLICT 예외 발생");
        return ErrorResponseDto.errorResponse(exception.getErrorCode().getErrorCode(), exception.getMessage());
    }

    /*entity 조회 예외 처리*/
    @ExceptionHandler(EntityNotFoundException.class)
    public ResponseEntity<ErrorResponseDto> handleEntityNotFoundException(EntityNotFoundException exception){

        log.error("HttpStatus.NOT_FOUND 예외 발생");
        return ErrorResponseDto.errorResponse(exception.getErrorCode().getErrorCode(), exception.getMessage());
    }

    /*권한이 없는 경우*/
    @ExceptionHandler(ForbiddenException.class)
    public ResponseEntity<ErrorResponseDto> handleForbiddenException(ForbiddenException exception){
        log.error("HttpStatus.FORBIDDEN 예외 발생");
        return ErrorResponseDto.errorResponse(exception.getErrorCode().getErrorCode(), exception.getMessage());
    }

    /*이메일이 기존과 동일한 경우*/
    @ExceptionHandler(EmailUnchangedException.class)
    public ResponseEntity<ErrorResponseDto> handleEmailException(EmailUnchangedException exception){
        log.error("HttpStatus.BAD_REQUEST email 예외 발생");
        return ErrorResponseDto.errorResponse(exception.getErrorCode().getErrorCode(), exception.getMessage());
    }

    /*비밀번호가 기존과 동일한 경우*/
    @ExceptionHandler(PasswordUnchangedException.class)
    public ResponseEntity<ErrorResponseDto> handlePasswordException(PasswordUnchangedException exception){
        log.error("HttpStatus.BAD_REQUEST password 예외 발생");
        return ErrorResponseDto.errorResponse(exception.getErrorCode().getErrorCode(), exception.getMessage());
    }

    /*로그인하지 않고 기능 사용하려고 할 때*/
    @ExceptionHandler(UnauthorizedAccessException.class)
    public void handleUnauthorizedAccessException(UnauthorizedAccessException exception){
        log.error("HttpStatus.FORBIDDEN 예외 발생");
    }

    /*로그인하지 않고 로그아웃을 하려고 할 때*/
    @ExceptionHandler(NotLoggedInException.class)
    public ResponseEntity<ErrorResponseDto> handleNotLoggedInException(NotLoggedInException exception){
        log.error("HttpStatus.FORBIDDEN password 예외 발생");
        return ErrorResponseDto.errorResponse(exception.getErrorCode().getErrorCode(), exception.getMessage());
    }

    /*검증 데이터가 유효하지 않은 경우*/
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ValidationErrorResponseDto> handleValidationException(MethodArgumentNotValidException exception){
        log.error("HttpStatus.BAB_REQUEST  예외 발생");

        /*검증 처리가 모두 유효하지 않은 경우*/
        List<FieldError> fieldErrors = exception.getBindingResult().getFieldErrors();
        List<String> fieldErrorList = fieldErrors.stream()
                .map(FieldError::getDefaultMessage)  // 각 필드의 오류 메시지를 가져온다.
                .toList();

        return ValidationErrorResponseDto.validationErrorResponse(exception.getStatusCode(), fieldErrorList);
    }
}