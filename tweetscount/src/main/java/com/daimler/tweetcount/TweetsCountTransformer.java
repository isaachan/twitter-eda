package com.daimler.tweetcount;

import com.daimler.tweetcount.model.Tweet;
import com.daimler.tweetcount.model.TweetCounter;
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
        var count = stateStore.get(tweet.getSender());
        if (count == null) {
            count = 0L;
        }
        if (tweet.getAction().equals("create_action")) {
            count++;
        }
        if (tweet.getAction().equals("delete_action")) {
            count--;
        }
        stateStore.put(tweet.getSender(), count);
        return new KeyValue<>(tweet.getSender(), count);
    }

    @Override
    public void close() {

    }
}
