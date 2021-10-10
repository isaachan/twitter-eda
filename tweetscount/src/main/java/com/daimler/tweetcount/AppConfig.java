package com.daimler.tweetcount;

import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.common.serialization.Serdes;
import org.apache.kafka.streams.KafkaStreams;
import org.apache.kafka.streams.StreamsBuilder;
import org.apache.kafka.streams.StreamsConfig;
import org.apache.kafka.streams.kstream.Consumed;
import org.apache.kafka.streams.kstream.Produced;
import org.apache.kafka.streams.state.Stores;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;

import java.util.Properties;
import java.util.regex.Pattern;

@Configuration
public class AppConfig {

    static String PATTERN_REGX = "tweets_topic|retweets_topic";
    public final static String TWEETS_COUNT = "tweets_count";
    public final static String TWEET_COUNT_STATE_STORE = "tweetCountStateStore";

    @Bean
    @Scope(ConfigurableBeanFactory.SCOPE_SINGLETON)
    public KafkaStreams kafkaStreams() {
        var builder = new StreamsBuilder();

        var storeSupplier = Stores.inMemoryKeyValueStore(TWEET_COUNT_STATE_STORE);
        var storeBuilder = Stores.keyValueStoreBuilder(storeSupplier, Serdes.Long(), Serdes.Long());
        builder.addStateStore(storeBuilder);

        builder
                .stream(Pattern.compile(PATTERN_REGX), Consumed.with(Serdes.String(), Serdes.String()))
                .transform(() -> new TweetsCountTransformer(TWEET_COUNT_STATE_STORE), TWEET_COUNT_STATE_STORE)
                .to(TWEETS_COUNT, Produced.with(Serdes.Long(), Serdes.Long()));

        var topology = builder.build();
        var kafkaStreams = new KafkaStreams(topology, props());
        kafkaStreams.start();
        return kafkaStreams;
    }

    private Properties props() {
        Properties props = new Properties();
        props.put(StreamsConfig.APPLICATION_ID_CONFIG, "tweet-counts");
        props.put(StreamsConfig.BOOTSTRAP_SERVERS_CONFIG, "localhost:9092");
        props.put(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG,"earliest");

        //For immediate results during testing
        props.put(StreamsConfig.CACHE_MAX_BYTES_BUFFERING_CONFIG, 0);
        props.put(StreamsConfig.COMMIT_INTERVAL_MS_CONFIG, 100);
        return props;
    }
}
