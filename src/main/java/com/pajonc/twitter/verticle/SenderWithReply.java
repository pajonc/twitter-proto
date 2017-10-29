package com.pajonc.twitter.verticle;

import com.pajonc.twitter.model.User;
import io.vertx.core.AbstractVerticle;
import io.vertx.core.eventbus.EventBus;

/**
 * Implemenation for Sender with reply.
 * It sends message only to one subscriber and register reply from recipient.
 */
public class SenderWithReply extends AbstractVerticle {

    private String channel;
    private User user;
    private String message;

    public SenderWithReply(String channel, User user, String message) {
        this.channel = channel;
        this.user = user;
        this.message = message;
    }

    @Override
    public void start() throws Exception {

        EventBus eb = vertx.eventBus();

        // Send a message every second
//        vertx.setPeriodic(1000, v -> {

            String message = "ping!";
            eb.send(this.channel, message, reply -> {
                user.addMessage(message);
                if (reply.succeeded()) {
                    // added only for test purposes Point to Point reply
                    System.out.println("Received reply " + reply.result().body());
                    user.addMessage((String) reply.result().body());
                } else {
                    System.out.println("No reply");
                }
            });

//        });
    }
}
