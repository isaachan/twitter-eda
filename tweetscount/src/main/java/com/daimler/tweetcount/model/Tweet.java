package com.daimler.tweetcount.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Tweet {

    private Long sender;
    private String action;

    public Long getSender() {
        return sender;
    }

    public void setSender(Long sender) {
        this.sender = sender;
    }

    public String getAction() {
        return action;
    }

    public void setAction(String action) {
        this.action = action;
    }


    public static Tweet build(String s) {
        try {
            return new ObjectMapper().readValue(s, Tweet.class);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

}
