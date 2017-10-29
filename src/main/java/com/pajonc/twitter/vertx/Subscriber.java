package com.pajonc.twitter.vertx;

import com.pajonc.twitter.model.User;

/**
 * Assign user to channel. Start listening for messages on assigned channel.
 */
public interface Subscriber {

    void subscribeChannel(String channel, User user);
}
