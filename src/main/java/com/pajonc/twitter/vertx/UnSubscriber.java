package com.pajonc.twitter.vertx;


import com.pajonc.twitter.model.User;

/**
 * Unassign user from channel. Stop getting messages from channel.
 */
public interface UnSubscriber {

    void unSubscribeChannel(String channel, User user);
}
