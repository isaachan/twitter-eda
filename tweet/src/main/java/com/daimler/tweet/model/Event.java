package com.daimler.tweet.model;

import com.fasterxml.jackson.databind.ObjectMapper;

public class Event<T extends WithSender> {

    public static final String CREATE = "create_action";
    public static final String DELETE = "delete_action";
    private T content;
    private String action;

    public Event() {}

    public Event(String action, T content) {
        this.action = action;
        this.content = content;
    }

    public String getAction() {
        return action;
    }

    public void setAction(String action) {
        this.action = action;
    }

    public T getContent() {
        return content;
    }

    public void setContent(T content) {
        this.content = content;
    }

    public Long getSender() {
        return content.getSender();
    }

    public String toJson() {
        try {
            return new ObjectMapper().writeValueAsString(this);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
