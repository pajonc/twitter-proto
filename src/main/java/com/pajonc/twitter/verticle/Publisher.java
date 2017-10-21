package com.pajonc.twitter.verticle;

import com.pajonc.twitter.model.User;
import io.vertx.core.AbstractVerticle;
import io.vertx.core.eventbus.EventBus;

public class Publisher extends AbstractVerticle {

    private String address;
    private User user;
    private String message;

    public Publisher(String address, User user, String message) {
        this.address = address;
        this.user = user;
        this.message = message;
    }

    @Override
    public void start() throws Exception {

        EventBus eb = vertx.eventBus();

        // Send a message every second

        vertx.setPeriodic(1000, v -> {

            vertx.eventBus().publish(address, this.message);
            user.addMessage(this.message);

        });


    }


}
