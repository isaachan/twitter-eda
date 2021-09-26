package com.daimler.tweet.model;

public class Event<T extends WithSender> {

    private T content;

    public Event() {}

    public Event(T content) {
        this.content = content;
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
