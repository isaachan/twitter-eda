package com.daimler.tweet.model;

public class Retweet implements WithSender {

    private long id;
    private long originalId;
    private String content;
    private Long sender;
    private Long timeline;

    public Retweet() {}

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getOriginalId() {
        return originalId;
    }

    public void setOriginalId(long originalId) {
        this.originalId = originalId;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Long getSender() {
        return sender;
    }

    public void setSender(Long sender) {
        this.sender = sender;
    }

    public Long getTimeline() {
        return timeline;
    }

    public void setTimeline(Long timeline) {
        this.timeline = timeline;
    }
}
