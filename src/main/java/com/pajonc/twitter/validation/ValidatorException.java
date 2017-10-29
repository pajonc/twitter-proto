package com.pajonc.twitter.validation;

public class ValidatorException extends RuntimeException {

    public ValidatorException(String message) {
        super(message);
    }

    @Override
    public String getMessage() {
        return "Validation exception: " + super.getMessage();
    }
}
