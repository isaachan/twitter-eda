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
        var tweetsOfDay = new ArrayList<Tweet>();
        tweetsOfDay.add(Tweet.build(value));
        timeline.put("2021-10-12", tweetsOfDay);
    }

    public List<com.daimler.tweet.model.Tweet> get(String date) {
        return timeline.get(date);
    }
}
