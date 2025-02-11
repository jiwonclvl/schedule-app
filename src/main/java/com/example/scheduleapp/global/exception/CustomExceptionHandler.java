package com.example.scheduleapp.global.exception;

import com.example.scheduleapp.global.exception.custom.CustomException;
import com.example.scheduleapp.global.exception.custom.PasswordException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Slf4j
@RestControllerAdvice
public class CustomExceptionHandler {

    /*로그인 기능 예외처리*/
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    @ExceptionHandler(CustomException.class)
    public ResponseEntity<ErrorResponseDto> handlePasswordException(PasswordException exception){

        log.error("HttpStatus.UNAUTHORIZED 예외 발생");

        return ErrorResponseDto.errorResponse(exception.getErrorCode(), exception.getMessage());
    }
}