package com.pajonc.twitter.integration;

import com.pajonc.twitter.cache.VertxTwitterCache;
import com.pajonc.twitter.model.User;
import com.pajonc.twitter.server.VertxTwitterServer;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertTrue;

/**
 * Integration test for messages twitting
 */
public class TwitterTest {

    private VertxTwitterServer vertxTwitterServer = new VertxTwitterServer();

    @Before
    public void setUp() {

        vertxTwitterServer.startServer();

    }

    @After
    public void tearDown() {

        vertxTwitterServer.stopServer();

    }


    @Test
    public void testSubcribingChannel() throws InterruptedException {

        //given
        User anton = new User("anton");
        User tom = new User("tom");
        User george = new User("george");
        String channelHobby = "hobby";
        String channelNews = "news";
        //when
        VertxTwitterCache.getInstance().registerUserInCache(anton);
        VertxTwitterCache.getInstance().registerUserInCache(tom);
        VertxTwitterCache.getInstance().registerUserInCache(george);

        vertxTwitterServer.subscribeChannel(channelHobby, anton);
        vertxTwitterServer.subscribeChannel(channelHobby, tom);
        vertxTwitterServer.subscribeChannel(channelNews, anton);
        vertxTwitterServer.publishMessage(channelHobby, george, "I like play tennins!");
        vertxTwitterServer.publishMessage(channelHobby, george, "I like play football!");
        vertxTwitterServer.publishMessage(channelNews, george, "Again thunderstorm is coming!");
        //then
        // again lets wait for all messages to be delivered
        Thread.sleep(1000);
        assertTrue(anton.getMessages().size()==3);
        assertTrue(tom.getMessages().size()==2);
        assertTrue(george.getMessages().size()==3);
    }

    @Test
    public void testUnSubcribingChannel() throws InterruptedException {

        //given
        User anton = new User("anton");
        User george = new User("george");
        String channelHobby = "hobby";
        //when
        VertxTwitterCache.getInstance().registerUserInCache(anton);
        VertxTwitterCache.getInstance().registerUserInCache(george);
        vertxTwitterServer.subscribeChannel(channelHobby, anton);
        vertxTwitterServer.publishMessage(channelHobby, george, "I like play tennins!");
        //lets wait for consuming message by subscribed users
        Thread.sleep(1000);
        vertxTwitterServer.unSubscribeChannel(channelHobby, anton);
        vertxTwitterServer.publishMessage(channelHobby, george, "I like play football!");
        //then
        // again lets wait for all messages to be delivered
        Thread.sleep(1000);
        assertTrue(anton.getMessages().size()==1);
        assertTrue(george.getMessages().size()==2);
    }

}
