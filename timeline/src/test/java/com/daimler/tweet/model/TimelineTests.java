package com.daimler.tweet.model;

import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZoneId;
import java.util.Date;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class TimelineTests {

    Timeline timeline = new Timeline("1");

    String event_2021_10_12_3pm = "{\"content\":{\"id\":1000,\"content\":\"This is a tweet.\",\"sender\":1,\"timeline\":1634021617947},\"action\":\"create_action\",\"sender\":1}";
    String event_2021_10_12_4pm = "{\"content\":{\"id\":1001,\"content\":\"This is a tweet.\",\"sender\":1,\"timeline\":1634021621547},\"action\":\"create_action\",\"sender\":1}";
    String event_2021_10_13_8am = "{\"content\":{\"id\":1001,\"content\":\"This is a tweet.\",\"sender\":1,\"timeline\":1634083439329},\"action\":\"create_action\",\"sender\":1}";


    @Test
    public void add_tweet_by_time() {
        timeline.merge(event_2021_10_12_3pm);
        List<Tweet> tweetsOfTheDay = timeline.get("2021-10-12");
        assertEquals(1, tweetsOfTheDay.size());
        assertEquals("This is a tweet.", tweetsOfTheDay.get(0).getContent());
        assertEquals(1, tweetsOfTheDay.get(0).getSender());
        assertEquals(1000L, tweetsOfTheDay.get(0).getId());
    }

    @Test
    public void group_tweets_by_date() {
        var timeline = new Timeline("1");

        timeline.merge(event_2021_10_12_3pm);
        timeline.merge(event_2021_10_12_4pm);
        timeline.merge(event_2021_10_13_8am);

        assertEquals(2, timeline.get("2021-10-12").size());
        assertEquals(1, timeline.get("2021-10-13").size());

        assertEquals(0, timeline.get("1970-01-01").size());
    }
}
