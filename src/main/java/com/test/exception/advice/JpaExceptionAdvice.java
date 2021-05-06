package com.test.exception.advice;

import com.test.dto.ResponseMessage;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class JpaExceptionAdvice {

    @ExceptionHandler(EmptyResultDataAccessException.class)
    public ResponseEntity<ResponseMessage> emptyResultDataAccessEx() {
        ResponseMessage responseMessage = new ResponseMessage("삭제 결과가 없습니다.");
        return new ResponseEntity<>(responseMessage, HttpStatus.NOT_FOUND);
    }
}
