package com.pajonc.twitter.validation;

public interface Validator<T> {

    void validate(T obj);
}
