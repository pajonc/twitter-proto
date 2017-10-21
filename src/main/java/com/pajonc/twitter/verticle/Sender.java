package com.pajonc.twitter.verticle;

import com.pajonc.twitter.model.User;
import io.vertx.core.AbstractVerticle;
import io.vertx.core.eventbus.EventBus;

public class Sender extends AbstractVerticle {

    private String address;
    private User user;

    public Sender(String address, User user) {
        this.address = address;
    }

    @Override
    public void start() throws Exception {

        EventBus eb = vertx.eventBus();

        // Send a message every second
        vertx.setPeriodic(1000, v -> {

            String message = "ping!";
            eb.send(this.address, message, reply -> {
                user.addMessage(message);
                if (reply.succeeded()) {
                    // added only for test purposes Point to Point reply
                    System.out.println("Received reply " + reply.result().body());
                    user.addMessage((String) reply.result().body());
                } else {
                    System.out.println("No reply");
                }
            });

        });
    }
}
