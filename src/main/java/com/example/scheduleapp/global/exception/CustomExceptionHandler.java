package com.example.scheduleapp.global.exception;

import com.example.scheduleapp.global.exception.custom.EntityNotFoundException;
import com.example.scheduleapp.global.exception.custom.LoginFailedException;
import com.example.scheduleapp.global.exception.custom.SignUpFailedException;
import com.example.scheduleapp.global.exception.dto.ErrorResponseDto;
import com.example.scheduleapp.global.exception.dto.ValidationErrorResponseDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import java.util.List;


@Slf4j
@RestControllerAdvice
public class CustomExceptionHandler {

    /*로그인 기능 예외처리*/
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    @ExceptionHandler(LoginFailedException.class)
    public ResponseEntity<ErrorResponseDto> handlePasswordException(LoginFailedException exception){

        log.error("HttpStatus.UNAUTHORIZED 예외 발생");
        return ErrorResponseDto.errorResponse(exception.getErrorCode().getErrorCode(), exception.getMessage());
    }

    /*검증 데이터가 유효하지 않은 경우*/
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ValidationErrorResponseDto> handleValidationException(MethodArgumentNotValidException exception){
        log.error("HttpStatus.BAB_REQUEST 예외 발생");

        /*검증 처리가 모두 유효하지 않은 경우*/
        List<FieldError> fieldErrors = exception.getBindingResult().getFieldErrors();
        List<String> fieldErrorList = fieldErrors.stream()
                .map(FieldError::getDefaultMessage)  // 각 필드의 오류 메시지를 가져온다.
                .toList();

        return ValidationErrorResponseDto.validationErrorResponse(exception.getStatusCode(), fieldErrorList);

    }

    /*회원 가입 예외 처리*/
    @ResponseStatus(HttpStatus.CONFLICT)
    @ExceptionHandler(SignUpFailedException.class)
    public ResponseEntity<ErrorResponseDto> handlePasswordException(SignUpFailedException exception){

        log.error("HttpStatus.CONFLICT 예외 발생");
        return ErrorResponseDto.errorResponse(exception.getErrorCode().getErrorCode(), exception.getMessage());
    }


    /*entity 조회 예외 처리*/
    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler(EntityNotFoundException.class)
    public ResponseEntity<ErrorResponseDto> handleEntityNotFoundException(EntityNotFoundException exception){

        log.error("HttpStatus.NOT_FOUND 예외 발생");
        return ErrorResponseDto.errorResponse(exception.getErrorCode().getErrorCode(), exception.getMessage());
    }
}