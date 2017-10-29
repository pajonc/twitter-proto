package com.pajonc.twitter.cache;

import com.pajonc.twitter.model.User;
import org.junit.Test;

import java.util.HashMap;
import java.util.Map;

import static org.junit.Assert.*;

public class VertxTwitterCacheTest {


    @Test
    public void shouldGetNotNullUsersHeap(){
        assertNotNull(VertxTwitterCache.getInstance().getUsersHeap());
    }

    @Test
    public void shouldGetUserCache() {
        //when
        User user = new User("fakeUser");
        HashMap<String, String> vertxMap = new HashMap<>();
        String vertxId = "vertxId";
        String channel = "channel";
        vertxMap.put(channel, vertxId);
        VertxTwitterCache.getInstance().getUsersHeap().put(user, vertxMap);
        //given
        Map<String, String> userCache = VertxTwitterCache.getInstance().getUsersHeap().get(user);
        //then
        assertNotNull(userCache);
        assertEquals(vertxId,userCache.get(channel));
        assertNull(userCache.get("fakeChannel"));
    }

    @Test(expected = RuntimeException.class)
    public void shouldFailGetUserCache(){
        //when
        User user = new User("fakeUser2");
        //given
        VertxTwitterCache.getInstance().getUserCache(user);
    }

    @Test
    public void shouldRegisterUserInCache(){
        //when
        User user = new User("fakeUser");
        //given
        VertxTwitterCache.getInstance().registerUserInCache(user);
        //then
        assertNotNull(VertxTwitterCache.getInstance().getUsersHeap().get(user));
    }



}