package com.pajonc.twitter.validation;

import io.vertx.core.Vertx;

public class VertxServerValidation implements Validator<Vertx> {

    @Override
    public void validate(Vertx obj) {
        if (obj == null) {
            throw new ValidatorException("Vertx Server not initialized!");
        }
    }
}
