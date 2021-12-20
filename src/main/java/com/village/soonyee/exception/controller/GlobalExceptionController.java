package com.village.soonyee.exception.controller;

import com.village.soonyee.exception.ErrorResponse;
import com.village.soonyee.exception.exception.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Slf4j
@RestControllerAdvice
@Order(Ordered.HIGHEST_PRECEDENCE)
public class GlobalExceptionController {
    @ExceptionHandler
    public ResponseEntity<ErrorResponse> handleUserNoExistsException(MemberNotExistsException ex){
        log.error("UserNoExistsException", ex);
        ErrorResponse response = new ErrorResponse(ex.getErrorCode());

        return new ResponseEntity<>(response, HttpStatus.valueOf(ex.getErrorCode().getStatus()));
    }
    @ExceptionHandler
    public ResponseEntity<ErrorResponse> handleDiaryNoExistsException(DiaryNotExistsException ex){
        log.error("DiaryNoExistsException", ex);
        ErrorResponse response = new ErrorResponse(ex.getErrorCode());

        return new ResponseEntity<>(response, HttpStatus.valueOf(ex.getErrorCode().getStatus()));
    }
    @ExceptionHandler
    public ResponseEntity<ErrorResponse> handleNotSameTargetException(NotSameTargetException ex){
        log.error("NotSameTarget", ex);
        ErrorResponse response = new ErrorResponse(ex.getErrorCode());

        return new ResponseEntity<>(response, HttpStatus.valueOf(ex.getErrorCode().getStatus()));
    }
    @ExceptionHandler
    public ResponseEntity<ErrorResponse> handleMemberNotSameException(MemberNotSameException ex){
        log.error("MemberNotSame", ex);
        ErrorResponse response = new ErrorResponse(ex.getErrorCode());

        return new ResponseEntity<>(response, HttpStatus.valueOf(ex.getErrorCode().getStatus()));
    }
    @ExceptionHandler
    public ResponseEntity<ErrorResponse> handleFileNotExistsException(FileNotExistsException ex){
        log.error("FileNotExist", ex);
        ErrorResponse response = new ErrorResponse(ex.getErrorCode());

        return new ResponseEntity<>(response, HttpStatus.valueOf(ex.getErrorCode().getStatus()));
    }
    @ExceptionHandler
    public ResponseEntity<ErrorResponse> handleWrongPathException(WrongPathException ex){
        log.error("WrongPath", ex);
        ErrorResponse response = new ErrorResponse(ex.getErrorCode());

        return new ResponseEntity<>(response, HttpStatus.valueOf(ex.getErrorCode().getStatus()));
    }
}
