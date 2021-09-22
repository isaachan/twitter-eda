package com.daimler.architecture.model;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
public class TweetController {

    private static final String TWEETS_TOPIC = "tweets_topic";
    private static final String RETWEETS_TOPIC = "retweets_topic";

    @Autowired
    private KafkaProducer<String, String> kafkaProducer;

    @PostMapping("/tweets/")
    public void postTweet(@RequestBody Tweet tweet) {
        System.out.println("Post tweet " + tweet);
        kafkaProducer.send(new ProducerRecord<>(TWEETS_TOPIC, String.valueOf(tweet.getId()), convertToJson(tweet)));
    }

    @DeleteMapping("/tweets/{id}")
    public void deleteTweet(@PathVariable long id) {
        System.out.println("Delete tweet with id " + id);
        kafkaProducer.send(new ProducerRecord<>(TWEETS_TOPIC, String.valueOf(id), null));
    }

    @PostMapping("/retweets/")
    public void retweet(@RequestBody Retweet retweet) {
        System.out.println("retweet the tweet " + retweet.getOriginalId());
        kafkaProducer.send(new ProducerRecord<>(RETWEETS_TOPIC, String.valueOf(retweet.getId()), convertToJson(retweet)));
    }

    @DeleteMapping("/retweets/{id}")
    public void deleteRetweet(@PathVariable long id) {
        System.out.println("delete retweet " + id);
        kafkaProducer.send(new ProducerRecord<>(RETWEETS_TOPIC, String.valueOf(id), null));
    }

    private String convertToJson(Object object) {
        try {
            return new ObjectMapper().writeValueAsString(object);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}