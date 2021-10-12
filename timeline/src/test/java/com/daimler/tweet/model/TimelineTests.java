package com.daimler.tweet.model;

import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZoneId;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class TimelineTests {

    @Test
    public void add_tweet_by_time() {
        var timeline = new Timeline("1");

        // coming event is raised at 2021-10-12 3pm.
        timeline.merge("{\"content\":{\"id\":1000,\"content\":\"This is a tweet.\",\"sender\":1,\"timeline\":1634021617947},\"action\":\"create_action\",\"sender\":1}");
        List<Tweet> tweetsOfTheDay = timeline.get("2021-10-12");
        assertEquals(1, tweetsOfTheDay.size());
    }

}
