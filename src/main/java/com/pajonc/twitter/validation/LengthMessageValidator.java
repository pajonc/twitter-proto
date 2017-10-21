package com.pajonc.twitter.validation;

public class LengthMessageValidator implements Validator<String> {

    @Override
    public void validate(String obj) {
        if (obj.length() > 140) {
            throw new ValidatorException(obj);
        }

    }
}
