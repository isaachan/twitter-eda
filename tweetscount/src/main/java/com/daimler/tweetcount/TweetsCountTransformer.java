package com.daimler.tweetcount;

import com.daimler.tweetcount.model.TweetCounter;
import org.apache.kafka.streams.KeyValue;
import org.apache.kafka.streams.kstream.Transformer;
import org.apache.kafka.streams.processor.ProcessorContext;

public class TweetsCountTransformer implements Transformer<String, String, KeyValue<String, TweetCounter>> {

    private String stateStore;
    private ProcessorContext context;

    public TweetsCountTransformer(String stateStore) {
        this.stateStore = stateStore;
    }

    @Override
    public void init(ProcessorContext context) {

    }

    @Override
    public KeyValue<String, TweetCounter> transform(String key, String value) {
        return null;
    }

    @Override
    public void close() {

    }
}
