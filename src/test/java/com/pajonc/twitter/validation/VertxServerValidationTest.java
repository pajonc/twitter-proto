package com.pajonc.twitter.validation;

import io.vertx.core.Vertx;
import org.junit.Test;

import static org.junit.Assert.assertNotNull;


public class VertxServerValidationTest {

    private VertxServerValidation vertxServerValidation = new VertxServerValidation();
    private Vertx server = null;

    @Test(expected = ValidatorException.class)
    public void shouldFailValidate() throws Exception {
        vertxServerValidation.validate(server);
    }

    @Test
    public void shouldPassValidate() throws Exception {
        //when
        server = Vertx.vertx();
        //given
        vertxServerValidation.validate(server);
        //then
        assertNotNull(server);
        //tearDown
        server.close();
        server=null;
    }


}