package com.test.exception.custom;

public class NoDataException extends RuntimeException {

    public NoDataException() {
        super();
    }

    public NoDataException(String message) {
        super(message);
    }
}
