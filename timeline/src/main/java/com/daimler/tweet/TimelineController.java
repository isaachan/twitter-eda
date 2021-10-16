package com.daimler.tweet;

import com.daimler.tweet.model.Timeline;
import com.daimler.tweet.model.Tweet;
import com.daimler.tweet.model.UserNotFoundException;
import org.apache.kafka.streams.KafkaStreams;
import org.apache.kafka.streams.StoreQueryParameters;
import org.apache.kafka.streams.state.QueryableStoreTypes;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
public class TimelineController {

    @Autowired
    private KafkaStreams stream;

    @GetMapping("/users/{userid}/tweets?from={from}&to={to}")
    public Map<String, List<Tweet>> findTweetsBetween(@PathVariable Long userid,
                                                      @RequestParam("from") Optional<String> from,
                                                      @RequestParam("to") Optional<String> to) {

        String storeName = TimelineStreamTopology.TIMELINE_STATE_STORE;
        var queryableStoreType = QueryableStoreTypes.<Long, Timeline>keyValueStore();
        var store = stream.store(StoreQueryParameters.fromNameAndType(storeName, queryableStoreType));
        var timeline = store.get(userid);
        if (timeline == null) throw new UserNotFoundException(userid);
        else return timeline.findBetween(from.get(), to.get());
    }
}
