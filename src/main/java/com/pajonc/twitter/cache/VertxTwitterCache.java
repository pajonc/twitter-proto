package com.pajonc.twitter.cache;


import com.pajonc.twitter.model.User;

import java.util.HashMap;
import java.util.Map;

/**
 * Specific Cache implementation for Vertx usage
 * Singleton made programatically for demo purposes!
 */
public class VertxTwitterCache {

    private static VertxTwitterCache vertxTwitterCache = null;

    /**
     * Caches user information. Map user and channel per verticleId.
     */
    private Map<User, Map<String, String>> usersHeap = new HashMap<>();

    private VertxTwitterCache() {
    }

    public static VertxTwitterCache getInstance() {
        if (vertxTwitterCache == null) {
            vertxTwitterCache = new VertxTwitterCache();
        }
        return vertxTwitterCache;
    }

    public Map<User, Map<String, String>> getUsersHeap() {
        return usersHeap;
    }

    public Map<String, String> getUserCache(User user) {
        if (usersHeap.get(user) == null) {
            throw new RuntimeException("User is not assigned in cache " + user.getName());
        }
        return usersHeap.get(user);
    }

    /**
     * Register user in cache
     * @param user
     */
    public void registerUserInCache(User user) {
        usersHeap.put(user, new HashMap<>());
    }

}
