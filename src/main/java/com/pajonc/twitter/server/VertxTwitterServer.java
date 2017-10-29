package com.pajonc.twitter.server;

import com.pajonc.twitter.cache.VertxTwitterCache;
import com.pajonc.twitter.model.User;
import com.pajonc.twitter.validation.VertxServerValidation;
import com.pajonc.twitter.verticle.Publisher;
import com.pajonc.twitter.verticle.Receiver;
import com.pajonc.twitter.vertx.Subscriber;
import com.pajonc.twitter.vertx.UnSubscriber;
import io.vertx.core.Vertx;

import java.util.Map;

/**
 * Basically this class should have single responsibility, but for demo simplification/usage made more complex.
 * Thus implement different interfaces!
 */
public class VertxTwitterServer implements Server<Vertx>, Subscriber, UnSubscriber {

    private Vertx vertx = null;
    private VertxServerValidation vertxServerValidation = new VertxServerValidation();

    /**
     * Start Vertx Bus
     */
    @Override
    public void startServer() {
        vertx = Vertx.vertx();
    }

    /**
     * Stop vertx bus
     */
    @Override
    public void stopServer() {
        this.vertx.close();
        this.vertx = null;
    }

    /**
     * This vertx bus/server need to be initialized before usage by calling startServer() method!
     *
     * @return vertx instance bus
     */
    @Override
    public Vertx getServer() {
        return vertx;
    }


    /**
     * Only one user accepted, due to the fact that it simulates http behaviour.
     * Start listening user on passed channel.
     *
     * @param channel
     * @param user
     */
    @Override
    public void subscribeChannel(String channel, User user) {
        Map<String, String> userVerticlesPerChannel = VertxTwitterCache.getInstance().getUserCache(user);
        vertxServerValidation.validate(this.getServer());
        this.getServer().deployVerticle(new Receiver(channel, user), res -> {
            if (res.succeeded()) {
                System.out.println("Channel " + channel + " deployed for user " + user);
                userVerticlesPerChannel.put(channel, res.result());
            }
        });
    }

    /**
     * Unsubscribe user from channel, user stop listening on this channel
     *
     * @param channel
     * @param user
     */
    @Override
    public void unSubscribeChannel(String channel, User user) {
        vertxServerValidation.validate(this.getServer());
        Map<String, String> userVerticlesPerChannel = VertxTwitterCache.getInstance().getUserCache(user);
        if (userVerticlesPerChannel.get(channel) == null) {
            throw new RuntimeException("Channel already unassigned");
        } else {
            this.getServer().undeploy(userVerticlesPerChannel.get(channel), res -> {
                        if (res.succeeded()) {
                            System.out.println("Channel " + channel + " undeployed for user " + user);
                            userVerticlesPerChannel.remove(res);
                        } else {
                            System.out.println("Issue with unassinging user " + user);
                            throw new RuntimeException(res.cause());
                        }
                    }
            );
        }
    }

    //Below methods should be also moved to another interfaces

    /**
     * Publish message to all channel subscribers
     *
     * @param channel to send
     * @param user    someone need to send a message
     * @param message
     */
    public void publishMessage(String channel, User user, String message) {
        vertxServerValidation.validate(this.getServer());
        Publisher publisher = new Publisher(channel, user, message);
        this.getServer().deployVerticle(publisher);
    }

}
