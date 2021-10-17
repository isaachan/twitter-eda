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
        var tweet = Tweet.build(jsonValue);
        var sender = tweet.getSender();
        var count = stateStore.get(sender);
        var tweetsCount = new TweetsCount(sender, count);
        tweetsCount.updateCount(tweet);

        stateStore.put(tweetsCount.getSender(), tweetsCount.getCount());
        return new KeyValue<>(tweetsCount.getSender(), tweetsCount.getCount());
    }

    @Override
    public void close() {

    }
}
