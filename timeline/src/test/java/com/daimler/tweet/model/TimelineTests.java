package com.daimler.tweet.model;

import org.junit.Before;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Date;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class TimelineTests {

    private Timeline timeline;

    String event_2021_10_12_3pm = "{\"content\":{\"id\":1000,\"content\":\"This is a tweet.\",\"sender\":1,\"timeline\":1634021617947},\"action\":\"create_action\",\"sender\":1}";
    String delet_event_2021_10_12_3pm = "{\"content\":{\"id\":1000,\"content\":\"This is a tweet.\",\"sender\":1,\"timeline\":1634021617947},\"action\":\"delete_action\",\"sender\":1}";
    String event_2021_10_12_4pm = "{\"content\":{\"id\":1001,\"content\":\"This is a tweet.\",\"sender\":1,\"timeline\":1634021621547},\"action\":\"create_action\",\"sender\":1}";
    String event_2021_10_13_8am = "{\"content\":{\"id\":1002,\"content\":\"This is a tweet.\",\"sender\":1,\"timeline\":1634108021547},\"action\":\"create_action\",\"sender\":1}";
    String event_2021_10_14_8am = "{\"content\":{\"id\":1003,\"content\":\"This is a tweet.\",\"sender\":1,\"timeline\":1634194421547},\"action\":\"create_action\",\"sender\":1}";
    String event_2021_10_15_8am = "{\"content\":{\"id\":1004,\"content\":\"This is a tweet.\",\"sender\":1,\"timeline\":1634280821547},\"action\":\"create_action\",\"sender\":1}";

    @BeforeEach
    public void setup() {
        timeline = new Timeline("1");
    }

    @Test
    public void add_tweet_by_time() {
        timeline.merge(event_2021_10_12_3pm);
        List<Tweet> tweetsOfTheDay = timeline.getByDate("2021-10-12");

        assertEquals(1, tweetsOfTheDay.size());
        assertEquals("This is a tweet.", tweetsOfTheDay.get(0).getContent());
        assertEquals(1, tweetsOfTheDay.get(0).getSender());
        assertEquals(1000L, tweetsOfTheDay.get(0).getId());
        assertEquals(Tweet.CREATE, tweetsOfTheDay.get(0).getAction());
    }

    @Test
    public void group_tweets_by_date() {
        timeline.merge(event_2021_10_12_3pm);
        timeline.merge(event_2021_10_12_4pm);
        timeline.merge(event_2021_10_13_8am);

        assertEquals(2, timeline.getByDate("2021-10-12").size());
        assertEquals(1, timeline.getByDate("2021-10-13").size());
        assertEquals(0, timeline.getByDate("1970-01-01").size());
    }

    @Test
    public void delete_tweet_from_timeline() {
        timeline.merge(event_2021_10_12_3pm);
        timeline.merge(event_2021_10_12_4pm);
        assertEquals(2, timeline.getByDate("2021-10-12").size());

        timeline.merge(delet_event_2021_10_12_3pm);
        assertEquals(1, timeline.getByDate("2021-10-12").size());
    }

    @Test
    public void find_tweets_between_dates() {
        timeline.merge(event_2021_10_12_3pm);
        timeline.merge(event_2021_10_12_4pm);
        timeline.merge(event_2021_10_13_8am);
        timeline.merge(event_2021_10_14_8am);
        timeline.merge(event_2021_10_15_8am);

        assertEquals(3, timeline.findBetween("2021-10-12", "2021-10-14").size());
    }
}
