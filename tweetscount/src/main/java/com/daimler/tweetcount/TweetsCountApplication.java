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
public class TweetsCountApplication {

    static String PATTERN_REGX = "tweets_topic|retweets_topic";
    public final static String TWEETS_COUNT = "tweets_count";
    public final static String TWEET_COUNT_STATE_STORE = "tweetCountStateStore";

    public static void main(String[] args) {
        SpringApplication app = new SpringApplication(TweetsCountApplication.class);
        app.setDefaultProperties(Collections.singletonMap("server.port", "8083"));
        app.run(args);
    }

}
