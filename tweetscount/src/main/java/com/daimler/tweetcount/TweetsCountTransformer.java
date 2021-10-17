package com.daimler.tweetcount;

import com.daimler.tweetcount.model.Tweet;
import org.apache.kafka.streams.KeyValue;
import org.apache.kafka.streams.kstream.Transformer;
import org.apache.kafka.streams.processor.ProcessorContext;
import org.apache.kafka.streams.state.KeyValueStore;

public class TweetsCountTransformer implements Transformer<String, String, KeyValue<Long, Long>> {

    private String storeName;
    private KeyValueStore<Long, Long> stateStore;

    public TweetsCountTransformer(String storeName) {
        this.storeName = storeName;
    }

    @Override
    public void init(ProcessorContext context) {
        this.stateStore = context.getStateStore(storeName);
    }

    @Override
    public KeyValue<Long, Long> transform(String key, String jsonValue) {
        var sender = Long.parseLong(key);
        var count = updateCount(stateStore.get(sender), Tweet.build(jsonValue));
        stateStore.put(sender, count);
        return new KeyValue<>(sender, count);
    }

    public long updateCount(Long current, Tweet tweet) {
        long result = current == null ? 0:current;
        if (tweet.getAction().equals("create_action")) {
            return result + 1;
        }
        else if (tweet.getAction().equals("delete_action")) {
            return result - 1;
        }
        throw new RuntimeException("Unknown Action: " + tweet.getAction());
    }

    @Override
    public void close() { }
}
