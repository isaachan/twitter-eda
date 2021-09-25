package com.daimler.architecture;

import com.daimler.architecture.model.Tweet;
import com.daimler.architecture.model.TweetCounter;
import org.apache.kafka.streams.kstream.ValueTransformer;
import org.apache.kafka.streams.processor.ProcessorContext;

public class TweetsCountTransformer implements ValueTransformer<Tweet, TweetCounter> {

    private String stateStore;

    public TweetsCountTransformer(String stateStore) {
        this.stateStore = stateStore;
    }

    @Override
    public void init(ProcessorContext context) {

    }

    @Override
    public TweetCounter transform(Tweet value) {
        return null;
    }

    @Override
    public void close() {

    }
}
