package com.daimler.tweet.model;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;

public class Timeline {

    private String sender;
    private Map<String, List<Tweet>> timeline = new HashMap<>();

    public Timeline(String sender) {
        this.sender = sender;
    }

    public void merge(String value) {
        var tweet = Tweet.build(value);
        String key = parseTimeOfTweet(tweet);
        var tweetsOfDay = timeline.getOrDefault(key, new ArrayList<>());

        if (tweet.getAction().equals(Tweet.CREATE)) {
            tweetsOfDay.add(tweet);
            timeline.put(key, tweetsOfDay);
        } else if (tweet.getAction().equals(Tweet.DELETE)) {
            tweetsOfDay.removeIf(t -> t.getId().equals(tweet.getId()));
        }
    }

    private String parseTimeOfTweet(Tweet tweet) {
        Date date = new Date(tweet.getTimeline());
        DateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        formatter.setTimeZone(TimeZone.getTimeZone("UTC"));
        return formatter.format(date);
    }

    public List<Tweet> getByDate(String date) {
        return timeline.getOrDefault(date, new ArrayList<>());
    }

    public Map<String, List<Tweet>> findBetween(String from, String to) {
        var selected = new HashMap<String, List<Tweet>>();
        for (var entry : timeline.entrySet()) {
            if (dateInbetwee(from, to, entry.getKey())) {
                selected.put(entry.getKey(), entry.getValue());
            }
        }
        return selected;
    }

    private boolean dateInbetwee(String from, String to, String date) {
        return date.compareTo(from) >= 0 && date.compareTo(to) <= 0;
    }

    /*
     *  Below methods are for serilization/deserilization to work.
     */
    public Timeline() {}

    public String getSender() {
        return sender;
    }

    public void setSender(String sender) {
        this.sender = sender;
    }

    public Map<String, List<Tweet>> getTimeline() {
        return timeline;
    }

    public void setTimeline(Map<String, List<Tweet>> timeline) {
        this.timeline = timeline;
    }

}
