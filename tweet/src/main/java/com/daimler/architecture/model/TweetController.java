package com.daimler.architecture.model;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
public class TweetController {

    private static final String TWEETS_TOPIC = "tweets_topic";
    
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

    private String convertToJson(Tweet tweet) {
        try {
            return new ObjectMapper().writeValueAsString(tweet);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
