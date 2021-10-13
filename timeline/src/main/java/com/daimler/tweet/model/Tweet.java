package com.daimler.tweet.model;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.HashMap;

public class Tweet {

    public static final String CREATE = "create_action";
    public static final String DELETE = "delete_action";

    private Long sender;
    private Long timeline;
    private String content;
    private Long id;
    private String action;

    public void setSender(Long sender) {
        this.sender = sender;
    }

    public void setTimeline(Long timeline) {
        this.timeline = timeline;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Long getSender() {
        return sender;
    }

    public Long getTimeline() {
        return timeline;
    }

    public String getContent() {
        return content;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setAction(String action) {
        this.action = action;
    }

    public String getAction() {
        return action;
    }

    public static Tweet build(String json) {
        try {
            HashMap rawData = new ObjectMapper().readValue(json, HashMap.class);

            var tweet = new Tweet();
            tweet.setSender(Long.valueOf((int) rawData.get("sender")));
            tweet.setAction((String) rawData.get("action"));

            var content = (HashMap) rawData.get("content");
            tweet.setTimeline((long) content.get("timeline"));
            tweet.setContent((String) content.get("content"));
            tweet.setId(Long.valueOf((int) content.get("id")));
            return tweet;
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }

}
