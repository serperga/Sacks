package com.sacks.codeexercise.error;

public class NotFoundOrderErrorException extends RuntimeException{

    public NotFoundOrderErrorException(String message) {
        super(message);
    }
}
