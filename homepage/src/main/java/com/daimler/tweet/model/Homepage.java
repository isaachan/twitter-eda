package com.daimler.tweet.model;

import java.util.List;
import java.util.Map;

public class Homepage {

    private Long sender;

    private Map<Long, List<Tweet>> timelines;

    public Homepage() {
    }

    public Long getSender() {
        return sender;
    }

    public void setSender(Long sender) {
        this.sender = sender;
    }

    public Map<Long, List<Tweet>> getTimelines() {
        return timelines;
    }

    public void setTimelines(Map<Long, List<Tweet>> timelines) {
        this.timelines = timelines;
    }
}
