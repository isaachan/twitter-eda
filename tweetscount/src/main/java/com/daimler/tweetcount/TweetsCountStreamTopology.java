package com.daimler.tweetcount;

import org.apache.kafka.common.serialization.Serdes;
import org.apache.kafka.streams.KafkaStreams;
import org.apache.kafka.streams.StreamsBuilder;
import org.apache.kafka.streams.Topology;
import org.apache.kafka.streams.kstream.Consumed;
import org.apache.kafka.streams.kstream.Produced;
import org.apache.kafka.streams.state.Stores;

import java.util.regex.Pattern;

public class TweetsCountStreamTopology {

    static String PATTERN_REGX = "tweets_topic|retweets_topic";
    public final static String TWEETS_COUNT = "tweets_count";
    public final static String TWEET_COUNT_STATE_STORE = "tweetCountStateStore";

    public static Topology build() {
        var builder = new StreamsBuilder();

        var storeSupplier = Stores.inMemoryKeyValueStore(TWEET_COUNT_STATE_STORE);
        var storeBuilder = Stores.keyValueStoreBuilder(storeSupplier, Serdes.Long(), Serdes.Long());
        builder.addStateStore(storeBuilder);

        builder
                .stream(Pattern.compile(PATTERN_REGX), Consumed.with(Serdes.String(), Serdes.String()))
                .transform(() -> new TweetsCountTransformer(TWEET_COUNT_STATE_STORE), TWEET_COUNT_STATE_STORE)
                .to(TWEETS_COUNT, Produced.with(Serdes.Long(), Serdes.Long()));

        return builder.build();
    }
}