package com.test.exception.advice;

import com.test.dto.ResponseMessage;
import com.test.exception.custom.DateEqualsException;
import com.test.exception.custom.WeekNullException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class ValidateExceptionAdvice {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ResponseMessage> validationEx(MethodArgumentNotValidException exception) {
        ResponseMessage responseMessage =
                new ResponseMessage(exception.getBindingResult()
                        .getAllErrors()
                        .stream()
                        .findFirst()
                        .get()
                        .getDefaultMessage());
        return new ResponseEntity(responseMessage, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(DateEqualsException.class)
    public ResponseEntity<ResponseMessage> dateEqualsEx(Exception e) {
        ResponseMessage responseMessage = new ResponseMessage(e.getMessage());
        return new ResponseEntity<>(responseMessage, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(WeekNullException.class)
    public ResponseEntity<ResponseMessage> weekNullEx(Exception e) {
        ResponseMessage responseMessage = new ResponseMessage(e.getMessage());
        return new ResponseEntity<>(responseMessage, HttpStatus.BAD_REQUEST);
    }
}
