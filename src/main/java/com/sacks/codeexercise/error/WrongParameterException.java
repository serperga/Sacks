package com.sacks.codeexercise.error;

public class WrongParameterException extends RuntimeException{

    public WrongParameterException(String message) {
        super(message);
    }
}
