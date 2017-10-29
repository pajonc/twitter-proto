package com.pajonc.twitter.verticle;

import com.pajonc.twitter.model.User;
import io.vertx.core.AbstractVerticle;
import io.vertx.core.eventbus.EventBus;

/**
 * Class created only for test purpose to simulate Point two Point behaviour.
 * Get Response from Sender and reply with response.
 */
public class ReceiverWithReply extends AbstractVerticle {

    private String channel;
    private User user;

    public ReceiverWithReply(String channel, User user) {
        this.channel = channel;
        this.user = user;
    }

    @Override
    public void start() {
        EventBus eb = vertx.eventBus();

        eb.consumer(this.channel, message -> {
            System.out.println("Received message: " + message.body());
            user.addMessage((String) message.body());
            // Now send back reply
            message.reply("pong!");
            user.addMessage((String) message.body());
        });

    }
}
