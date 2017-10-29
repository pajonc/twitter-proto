package com.pajonc.twitter.server;

import io.vertx.ext.unit.Async;
import io.vertx.ext.unit.TestSuite;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class VertxTwitterServerTest {


    private VertxTwitterServer server;

    @Test
    public void testStartedServer() throws Exception {
        //when
        server = new VertxTwitterServer();
        //given
        server.startServer();
        //then
        assertNotNull(server.getServer());
    }

    @Test
    public void testNotStartedServer() throws Exception {
        //when
        server = new VertxTwitterServer();
        //given
        //nothing done, server not initialized
        //then
        assertNull(server.getServer());
    }


    @Test
    public void subscribeChannel() throws Exception {
        //todo
    }

    @Test
    public void unSubscribeChannel() throws Exception {
        //todo
    }

    TestSuite suite = TestSuite.create("the_test_suite");

    @Test
    public void test() {
        //todo

        suite.before(context -> {
            // Test suite setup

        }).test("my_test_case_1", context -> {
            // Test 1
            Async async = context.async();

        }).test("my_test_case_2", context -> {
            // Test 2
        }).test("my_test_case_3", context -> {
            // Test 3
        }).after(context -> {
            // Test suite cleanup
        });


    }


}