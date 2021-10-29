package com.daimler.tweetcount;

import com.daimler.tweetcount.model.Tweet;
import com.daimler.tweetcount.model.UserNotFoundException;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.streams.KeyValue;
import org.apache.kafka.streams.kstream.Transformer;
import org.apache.kafka.streams.processor.ProcessorContext;
import org.apache.kafka.streams.state.KeyValueStore;
import org.springframework.beans.factory.annotation.Autowired;

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
        Long result = stateStore.get(sender);
        if (result == null) throw new UserNotFoundException(sender);

        var count = updateCount(result, Tweet.build(jsonValue));
        stateStore.put(sender, count);
        return new KeyValue<>(sender, count);
    }

    public long updateCount(Long result, Tweet tweet) {
        if (tweet.getAction().equals("create_action")) {
            return result + 1;
        } else if (tweet.getAction().equals("delete_action")) {
            return result - 1;
        }
        throw new RuntimeException("Unknown Action: " + tweet.getAction());
    }

    @Override
    public void close() { }
}
