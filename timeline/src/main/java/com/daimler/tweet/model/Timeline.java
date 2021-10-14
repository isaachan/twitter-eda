package com.daimler.tweet.model;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.*;

public class Timeline {

    private String sender;
    private Map<String, List<Tweet>> timeline = new HashMap<>();

    public Timeline(String sender) {
        this.sender = sender;
    }

    public void merge(String value) {
        var tweet = Tweet.build(value);
        String key = parseTimeOfTweet(tweet);
        var tweetsOfDay = timeline.getOrDefault(key, new ArrayList<>());

        if (tweet.getAction().equals(Tweet.CREATE)) {
            tweetsOfDay.add(tweet);
            timeline.put(key, tweetsOfDay);
        } else if (tweet.getAction().equals(Tweet.DELETE)) {
            tweetsOfDay.removeIf(t -> t.getId().equals(tweet.getId()));
        }
    }

    private String parseTimeOfTweet(Tweet tweet) {
        Date date = new Date(tweet.getTimeline());
        DateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        formatter.setTimeZone(TimeZone.getTimeZone("UTC"));
        return formatter.format(date);
    }

    public List<Tweet> get(String date) {
        return timeline.getOrDefault(date, new ArrayList<>());
    }
}
