package com.pajonc.twitter.server;


public interface Server<T> {

    void startServer();

    void stopServer();

    T getServer();

}
