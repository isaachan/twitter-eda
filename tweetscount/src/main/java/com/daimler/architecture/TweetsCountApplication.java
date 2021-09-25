package com.daimler.architecture;

import com.daimler.architecture.model.Tweet;
import org.apache.kafka.common.serialization.Serdes;
import org.apache.kafka.streams.StreamsBuilder;
import org.apache.kafka.streams.kstream.Consumed;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.regex.Pattern;

@SpringBootApplication
public class TweetsCountApplication implements CommandLineRunner {

    static String PATTERN_REGX = "tweets_topic|retweets_topic";
    public static void main(String[] args) {
        SpringApplication.run(TweetsCountApplication.class, args);
    }

    @Override
    public void run(String... args) throws Exception {
        var builder = new StreamsBuilder();

        var tweetCountStateStore = "tweetCountStateStore";

        builder
                .stream(Pattern.compile(PATTERN_REGX), Consumed.with(Serdes.String(), Serdes.String()))
                .transform(() -> new TweetsCountTransformer(tweetCountStateStore), tweetCountStateStore);
    }
}
