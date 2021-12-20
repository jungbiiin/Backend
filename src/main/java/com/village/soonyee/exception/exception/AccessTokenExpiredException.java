package com.village.soonyee.exception.exception;


import com.village.soonyee.exception.ErrorCode;
import lombok.Getter;

/**
 * 사용자 정의 exception 클래스
 *
 * @version 1.3
 * @author 양시준
 */
@Getter
public class AccessTokenExpiredException extends RuntimeException {
    private final ErrorCode errorCode;

    public AccessTokenExpiredException(String message, ErrorCode errorCode) {
        super(message);
        this.errorCode = errorCode;
    }
}
