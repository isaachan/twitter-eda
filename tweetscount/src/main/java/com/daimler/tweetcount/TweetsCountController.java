package com.daimler.tweetcount;

import com.daimler.tweetcount.model.UserNotFoundException;
import org.apache.kafka.streams.KafkaStreams;
import org.apache.kafka.streams.StoreQueryParameters;
import org.apache.kafka.streams.state.QueryableStoreTypes;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
public class TweetsCountController {

    @Autowired
    KafkaStreams stream;

    @GetMapping("/tweets/{userId}/counts")
    public Long postTweet(@PathVariable long userId) {
        String storeName = TweetsCountApplication.TWEET_COUNT_STATE_STORE;
        var queryableStoreType = QueryableStoreTypes.<Long, Long>keyValueStore();
        var store = stream.store(StoreQueryParameters.fromNameAndType(storeName, queryableStoreType));
        var count = store.get(userId);
        if (count == null) throw new UserNotFoundException(userId);
        else return count;
    }
}
