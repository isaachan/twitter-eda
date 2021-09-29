package com.daimler.tweetcount;

import org.apache.kafka.streams.KafkaStreams;
import org.apache.kafka.streams.state.QueryableStoreTypes;
import org.apache.kafka.streams.state.ReadOnlyKeyValueStore;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
public class TweetsCountController {

    @Autowired
    KafkaStreams stream;

    @GetMapping("/tweets/{userId}/counts")
    public Long postTweet(@PathVariable long userId) {
        String storeName = TweetsCountApplication.TWEET_COUNT_STATE_STORE;
        var store = stream.store(storeName, QueryableStoreTypes.<Long, Long>keyValueStore());
        var count = store.get(userId);
        if (count == null) throw new UserNotFoundException(userId);
        else return count;
    }
}
