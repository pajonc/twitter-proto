package com.pajonc.twitter.verticle;

import com.pajonc.twitter.model.User;
import com.pajonc.twitter.validation.LengthMessageValidator;
import com.pajonc.twitter.validation.Validator;
import io.vertx.core.AbstractVerticle;
import io.vertx.core.eventbus.EventBus;

/**
 * Publisher verticle. It is going to send message to every user register on this channel.
 * Additionally stores user sent messages in user class.
 */
public class Publisher extends AbstractVerticle {

    private String channel;
    private User user;
    private String message;
    private Validator validator = new LengthMessageValidator();


    public Publisher(String channel, User user, String message) {
        validator.validate(message);
        this.channel = channel;
        this.user = user;
        this.message = message;
    }

    @Override
    public void start() throws Exception {

        EventBus eb = vertx.eventBus();

        // Send a message every second
//        vertx.setPeriodic(1000, v -> {
        vertx.eventBus().publish(channel, this.message);
        System.out.println("Message published: "+this.message+" by user "+this.user.getName());
        user.addMessage(this.message);
//        });


    }


}
