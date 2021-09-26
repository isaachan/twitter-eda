package com.daimler.tweet.model;

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
}
