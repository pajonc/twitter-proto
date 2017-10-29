package com.pajonc.twitter.validation;

import org.junit.Test;

import java.util.Random;

import static org.junit.Assert.*;

public class LengthMessageValidatorTest {

    private LengthMessageValidator lengthMessageValidator = new LengthMessageValidator();

    @Test
    public void shouldPassValidate(){
        lengthMessageValidator.validate(generateMessage(140));
    }

    @Test(expected = RuntimeException.class)
    public void shouldFailValidate(){
        lengthMessageValidator.validate(generateMessage(141));
    }

    private String generateMessage(int size) {

        StringBuilder builder = new StringBuilder();
        Random random = new Random();
        for (int i = 0; i < size ; i++) {
            builder.append(random.nextInt(10));
        }
        return builder.toString();
    }

}