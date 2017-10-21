package com.pajonc.twitter.server;

import com.pajonc.twitter.model.User;
import com.pajonc.twitter.verticle.Receiver;
import com.pajonc.twitter.verticle.Sender;
import io.vertx.core.Vertx;

import java.util.HashMap;
import java.util.Map;

public class TwitterServer implements Server {

    private Vertx vertx = Vertx.vertx();

    /**
     * Caches user information. Map user and channel per verticleId.
     */
    private static Map<User, Map<String, String>> usersHeap = new HashMap<>();

    @Override
    public void registerUser(User user) {
        usersHeap.put(user, null);
    }

    @Override
    public void subscribeChannel(String channel, User user) {
        Map<String, String> userVerticlesPerChannel = usersHeap.get(user);
        if (userVerticlesPerChannel.get(channel) != null) {
            System.out.println("Channel already assigned");
        } else {
            this.vertx.deployVerticle(new Receiver(channel, user), res -> {
                if (res.succeeded()) {
                    System.out.println("Channel" + channel + "deployed for user " + user);
                    userVerticlesPerChannel.put(channel, res.result());
                }
            });
        }
    }

    @Override
    public void unSubscribeChannel(String channel, User user) {
        Map<String, String> userVerticlesPerChannel = usersHeap.get(user);
        if (userVerticlesPerChannel.get(channel) != null) {
            throw new RuntimeException("Channel already unassigned");
        } else {
            this.vertx.undeploy(userVerticlesPerChannel.get(channel), res -> {
                        if (res.succeeded()) {
                            System.out.println("Channel" + channel + "undeployed for user " + user);
                        } else {
                            throw new RuntimeException("Channel already assigned");

                        }
                    }
            );
        }
    }

    @Override
    public void sendMessage(String channel, User user) {
        Sender sender = new Sender(channel, user);
        this.vertx.deployVerticle(sender, res -> {
            if (res.succeeded()) {
                // added only for test purposes, waits for message send
                try {
                    Thread.sleep(100);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

                this.vertx.undeploy(res.result());
            }
        });
    }

}
