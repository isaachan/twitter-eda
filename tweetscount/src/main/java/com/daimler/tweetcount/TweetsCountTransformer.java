package com.daimler.tweetcount;

import com.daimler.tweetcount.model.Tweet;
import org.apache.kafka.streams.KeyValue;
import org.apache.kafka.streams.kstream.Transformer;
import org.apache.kafka.streams.processor.ProcessorContext;
import org.apache.kafka.streams.state.KeyValueStore;

public class TweetsCountTransformer implements Transformer<String, String, KeyValue<Long, Long>> {

    private String storeName;
    private ProcessorContext context;
    private KeyValueStore<Long, Long> stateStore;

    public TweetsCountTransformer(String storeName) {
        this.storeName = storeName;
    }

    @Override
    public void init(ProcessorContext context) {
        this.context = context;
        this.stateStore = this.context.getStateStore(storeName);
    }

    @Override
    public KeyValue<Long, Long> transform(String key, String jsonValue) {
        var tweet = Tweet.build(jsonValue);
        var sender = tweet.getSender();
        var count = stateStore.get(sender);
        if (count == null) { count = 0L; }
        count = update(tweet, count);
        stateStore.put(sender, count);
        return new KeyValue<>(sender, count);
    }

    private Long update(Tweet tweet, Long count) {
        if (tweet.getAction().equals("create_action")) {
            return count + 1;
        }
        else if (tweet.getAction().equals("delete_action")) {
            return count - 1;
        }
        throw new RuntimeException();
    }

    @Override
    public void close() {

    }
}
