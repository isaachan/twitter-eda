package com.daimler.tweetcount;

import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.common.serialization.Serdes;
import org.apache.kafka.streams.KafkaStreams;
import org.apache.kafka.streams.StreamsBuilder;
import org.apache.kafka.streams.StreamsConfig;
import org.apache.kafka.streams.kstream.Consumed;
import org.apache.kafka.streams.kstream.Produced;
import org.apache.kafka.streams.state.Stores;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.Collections;
import java.util.Properties;
import java.util.regex.Pattern;

@SpringBootApplication
public class TweetsCountApplication implements CommandLineRunner {

    static String PATTERN_REGX = "tweets_topic|retweets_topic";
    public final static String TWEETS_COUNT = "tweets_count";
    public final static String TWEET_COUNT_STATE_STORE = "tweetCountStateStore";

    public static void main(String[] args) {
        SpringApplication app = new SpringApplication(TweetsCountApplication.class);
        app.setDefaultProperties(Collections.singletonMap("server.port", "8083"));
        app.run(args);
    }

    @Override
    public void run(String... args) throws Exception {
        var builder = new StreamsBuilder();

        var storeSupplier = Stores.inMemoryKeyValueStore(TWEET_COUNT_STATE_STORE);
        var storeBuilder = Stores.keyValueStoreBuilder(storeSupplier, Serdes.Long(), Serdes.Long());
        builder.addStateStore(storeBuilder);

        builder
                .stream(Pattern.compile(PATTERN_REGX), Consumed.with(Serdes.String(), Serdes.String()))
                .transform(() -> new TweetsCountTransformer(TWEET_COUNT_STATE_STORE), TWEET_COUNT_STATE_STORE)
                .to(TWEETS_COUNT, Produced.with(Serdes.Long(), Serdes.Long()));

        var topology = builder.build();
        new KafkaStreams(topology, props()).start();
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
