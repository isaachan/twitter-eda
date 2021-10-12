package com.daimler.tweet.model;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

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
        try {
            doMerge(value);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }

    private void doMerge(String value) throws JsonProcessingException {
        var rawData = new ObjectMapper().readValue(value, HashMap.class);
        var content = (HashMap) rawData.get("content");
        var tweet = new Tweet();
        tweet.setSender(Long.valueOf((int) rawData.get("sender")));
        tweet.setTimeline((long) content.get("timeline"));
        tweet.setContent((String) content.get("content"));

        var tweetsOfDay = new ArrayList<Tweet>();
        tweetsOfDay.add(tweet);
        timeline.put("2021-10-12", tweetsOfDay);
    }

    public List<com.daimler.tweet.model.Tweet> get(String date) {
        return timeline.get(date);
    }
}
