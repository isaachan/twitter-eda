package com.daimler.tweet;

import com.daimler.tweet.model.Timeline;
import org.apache.kafka.streams.KeyValue;
import org.apache.kafka.streams.kstream.Transformer;
import org.apache.kafka.streams.processor.ProcessorContext;
import org.apache.kafka.streams.processor.StateStore;
import org.apache.kafka.streams.state.KeyValueStore;

public class TimelineTransformer implements Transformer<String, String, KeyValue<String, Timeline>> {

    private final String storeName;
    private KeyValueStore<String, Timeline> stateStore;

    public TimelineTransformer(String storeName) {
        this.storeName = storeName;
    }

    @Override
    public void init(ProcessorContext context) {
        this.stateStore = context.getStateStore(storeName);
    }

    @Override
    public KeyValue<String, Timeline> transform(String key, String value) {
        var timeline = stateStore.get(key);
        if (timeline == null) { timeline = new Timeline(); }
        timeline.merge(value);
        stateStore.put(key, timeline);
        return new KeyValue<>(key, timeline);
    }

    @Override
    public void close() {

    }
}
