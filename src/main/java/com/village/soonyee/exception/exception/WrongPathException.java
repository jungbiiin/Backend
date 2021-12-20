package com.village.soonyee.exception.exception;

import com.village.soonyee.exception.ErrorCode;
import lombok.Getter;

@Getter
public class WrongPathException extends RuntimeException{
    private final ErrorCode errorCode;

    public WrongPathException(String message, ErrorCode errorCode) {
        super(message);
        this.errorCode = errorCode;
    }
}
