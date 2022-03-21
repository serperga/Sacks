package com.sacks.codeexercise.error;

public class IncorrectUpdateStatusException extends RuntimeException{

    public IncorrectUpdateStatusException(String message) {
        super(message);
    }
}
