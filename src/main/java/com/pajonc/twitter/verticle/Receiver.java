package com.pajonc.twitter.verticle;

import com.pajonc.twitter.model.User;
import io.vertx.core.AbstractVerticle;
import io.vertx.core.eventbus.EventBus;

/**
 * Receiver verticle implementation. Register on channel and listen for messages from Vertx Bus.
 *
 */
public class Receiver extends AbstractVerticle {

    private String channel;
    private User user;

    public Receiver(String channel, User user) {
        this.channel = channel;
        this.user = user;
    }

    @Override
    public void start() {
        EventBus eb = vertx.eventBus();

        eb.consumer(this.channel, message -> {
            System.out.println("Received message: " + message.body()+" by user "+user.getName());
            user.addMessage((String) message.body());
        });

    }

}
