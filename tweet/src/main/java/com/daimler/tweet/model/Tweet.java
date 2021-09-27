package com.daimler.tweet.model;

public class Tweet implements WithSender {

    private long id;
    private String content;
    private Long sender;
    private Long timeline;

    public Tweet() {}

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
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

    @Override
    public String toString() {
        return String.format("id=%d,send=%d,content=%s,timeline=%s",
                id, sender, content, timeline);
    }
}
