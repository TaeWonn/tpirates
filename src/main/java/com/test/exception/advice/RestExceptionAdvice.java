package com.test.exception.advice;

import com.test.dto.ResponseMessage;
import com.test.exception.custom.NoDataException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class RestExceptionAdvice {

    @ExceptionHandler(NoDataException.class)
    public ResponseEntity<ResponseMessage> noDataEx(Exception e) {
        ResponseMessage responseMessage = new ResponseMessage(e.getMessage());
        return new ResponseEntity<>(responseMessage, HttpStatus.BAD_REQUEST);
    }
}
