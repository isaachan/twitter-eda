package com.daimler.tweetcount;

import org.apache.kafka.streams.KeyValue;
import org.apache.kafka.streams.kstream.Transformer;
import org.apache.kafka.streams.processor.ProcessorContext;
import org.apache.kafka.streams.state.KeyValueStore;

public class UserUpdater implements Transformer<String, String, KeyValue<Long, Long>> {

    private String storeName;
    private KeyValueStore<Long, Long> stateStore;

    public UserUpdater(String storeName) {
        this.storeName = storeName;
    }

    @Override
    public void init(ProcessorContext context) {
        this.stateStore = context.getStateStore(storeName);
    }

    @Override
    public KeyValue<Long, Long> transform(String key, String value) {
        var user = this.stateStore.get(Long.parseLong(key));
        if (user == null) {
            this.stateStore.put(Long.parseLong(key), 0L);
        }
        return null;
    }

    @Override
    public void close() {

    }
}
