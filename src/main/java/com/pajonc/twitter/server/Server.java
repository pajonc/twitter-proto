package com.pajonc.twitter.server;

import com.pajonc.twitter.model.User;

public interface Server {

    void registerUser(User user);

    void subscribeChannel(String channel, User user);

    void unSubscribeChannel(String channel, User user);

    void sendMessage(String channel, User user);

}
