package com.daimler.tweet;

import com.daimler.tweet.model.TimelineSerdes;
import org.apache.kafka.common.serialization.Serdes;
import org.apache.kafka.streams.StreamsBuilder;
import org.apache.kafka.streams.Topology;
import org.apache.kafka.streams.kstream.Consumed;
import org.apache.kafka.streams.state.Stores;

import java.util.regex.Pattern;

public class TimelineStreamTopology {

    public static final String TIMELINE_STATE_STORE = "timelineStateStore";
    static String PATTERN_REGX = "tweets_topic|retweets_topic";

    public static Topology build() {
        var builder = new StreamsBuilder();
        var storeSupplier = Stores.inMemoryKeyValueStore(TIMELINE_STATE_STORE);
        var storeBuilder = Stores.keyValueStoreBuilder(storeSupplier, Serdes.Long(), TimelineSerdes.build());
        builder.addStateStore(storeBuilder);

        builder
                .stream(Pattern.compile(PATTERN_REGX), Consumed.with(Serdes.String(), Serdes.String()))
                .transform(() -> new TimelineTransformer(TIMELINE_STATE_STORE), TIMELINE_STATE_STORE);

        return builder.build();
    }
}
