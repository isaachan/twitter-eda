package com.daimler.tweet.model;

import org.junit.jupiter.api.Test;

import java.util.Date;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class EventTests {
    /**
     *  This test mainly provides the document for Event json.
     **/
    @Test
    public void convert_event_to_json() {
        Tweet tweet = new Tweet();
        tweet.setSender(1L);
        tweet.setContent("This is a tweet.");
        tweet.setTimeline(1634021617947L);
        tweet.setId(1000L);

        /*
         *  {"content":{"id":1000,"content":"This is a tweet.","sender":1,"timeline":1634021617947},"action":"create_action","sender":1}
         */
        String jsonOfTweetEvent = "{\"content\":{\"id\":1000,\"content\":\"This is a tweet.\",\"sender\":1,\"timeline\":1634021617947},\"action\":\"create_action\",\"sender\":1}";
        assertEquals(jsonOfTweetEvent, new Event(Event.CREATE, tweet).toJson());

        Retweet retweet = new Retweet();
        retweet.setContent("This is a retweet");
        retweet.setId(2000L);
        retweet.setOriginalId(1000L);
        retweet.setTimeline(1634021617947L);
        retweet.setSender(2L);

        /*
         *  {"content":{"id":2000,"originalId":1000,"content":"This is a retweet","sender":2,"timeline":1634021617947},"action":"delete_action","sender":2}
         */
        String jsonOfRetweetEvent = "{\"content\":{\"id\":2000,\"originalId\":1000,\"content\":\"This is a retweet\",\"sender\":2,\"timeline\":1634021617947},\"action\":\"delete_action\",\"sender\":2}";
        assertEquals(jsonOfRetweetEvent, new Event(Event.DELETE, retweet).toJson());
        System.out.println(new Event(Event.DELETE, retweet).toJson());
    }
}
