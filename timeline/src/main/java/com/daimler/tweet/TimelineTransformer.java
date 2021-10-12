package com.daimler.tweet;

import com.daimler.tweet.model.Timeline;
import org.apache.kafka.streams.KeyValue;
import org.apache.kafka.streams.kstream.Transformer;
import org.apache.kafka.streams.processor.ProcessorContext;

public class TimelineTransformer implements Transformer<String, String, KeyValue<Long, Timeline>> {

    private final String storeName;

    public TimelineTransformer(String storeName) {
        this.storeName = storeName;
    }

    @Override
    public void init(ProcessorContext context) {

    }

    @Override
    public KeyValue<Long, Timeline> transform(String key, String value) {
        return null;
    }

    @Override
    public void close() {

    }
}
