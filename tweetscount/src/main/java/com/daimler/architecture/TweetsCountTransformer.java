package com.daimler.architecture;

import com.daimler.architecture.model.Tweet;
import com.daimler.architecture.model.TweetCounter;
import org.apache.kafka.streams.KeyValue;
import org.apache.kafka.streams.kstream.Transformer;
import org.apache.kafka.streams.kstream.ValueTransformer;
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
