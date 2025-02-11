package com.example.scheduleapp.global.exception.custom;

import com.example.scheduleapp.global.exception.ErrorCode;
import lombok.Getter;

@Getter
public class CustomException extends RuntimeException {
    private final ErrorCode errorCode; //응답 상태 코드
    private final String message; //지정한 메세지


    //생성자를 통해 상태 코드와 내가 커스텀한 메세지를 받아서 CustomException 필드에 저장한다!
    public CustomException(ErrorCode errorCode) {
        super(errorCode.getErrorMessage());
        this.errorCode = errorCode;
        this.message = errorCode.getErrorMessage();
    }
}
