package com.village.soonyee.exception.exception;

import com.village.soonyee.exception.ErrorCode;
import lombok.Getter;

@Getter
public class MemberNotSameException extends RuntimeException{
    private final ErrorCode errorCode;

    public MemberNotSameException(String message, ErrorCode errorCode) {
        super(message);
        this.errorCode = errorCode;
    }
}
