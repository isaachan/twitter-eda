package com.daimler.tweetcount;

import com.daimler.tweetcount.model.Tweet;

public class TweetsCount {

    private long sender;
    private long count;

    public TweetsCount(long sender, Long count) {
        this.sender = sender;
        this.count = count == null? 0L : count;
    }

    public long getCount() {
        return count;
    }

    public long getSender() {
        return sender;
    }

    public void updateCount(Tweet tweet) {
        if (tweet.getAction().equals("create_action")) {
            this.count++;
        }
        else if (tweet.getAction().equals("delete_action")) {
            this.count--;
        }
        return;
    }
}

