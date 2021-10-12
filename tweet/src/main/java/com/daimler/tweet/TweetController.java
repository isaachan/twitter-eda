package com.daimler.tweet;

import com.daimler.tweet.model.Event;
import com.daimler.tweet.model.Retweet;
import com.daimler.tweet.model.Tweet;
import com.daimler.tweet.model.WithSender;
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
        kafkaProducer.send(new ProducerRecord<>(TWEETS_TOPIC, String.valueOf(tweet.getSender()), convertCreateEvent(tweet)));
    }

    @DeleteMapping("/tweets/{id}")
    public void deleteTweet(@PathVariable long id, @RequestBody Tweet tweet) {
        System.out.println("Delete tweet with id " + id);
        kafkaProducer.send(new ProducerRecord<>(TWEETS_TOPIC, String.valueOf(tweet.getSender()), convertDeleteEvent(tweet)));
    }

    @PostMapping("/retweets/")
    public void retweet(@RequestBody Retweet retweet) {
        System.out.println("retweet the tweet " + retweet.getOriginalId());
        kafkaProducer.send(new ProducerRecord<>(RETWEETS_TOPIC, String.valueOf(retweet.getSender()), convertCreateEvent(retweet)));
    }

    @DeleteMapping("/retweets/{id}")
    public void deleteRetweet(@PathVariable long id, @RequestBody Retweet retweet) {
        System.out.println("delete retweet " + id);
        kafkaProducer.send(new ProducerRecord<>(RETWEETS_TOPIC, String.valueOf(retweet.getSender()), convertDeleteEvent(retweet)));
    }


    private String convertCreateEvent(WithSender tweet) {
        return new Event(Event.CREATE, tweet).toJson();
//        return convertToJson(new Event(Event.CREATE, tweet));
    }

    private String convertDeleteEvent(WithSender tweet) {
        return new Event(Event.DELETE, tweet).toJson();
//        return convertToJson(new Event(Event.DELETE, tweet));
    }

//    private String convertToJson(Object object) {
//        try {
//            return new ObjectMapper().writeValueAsString(object);
//        } catch (Exception e) {
//            throw new RuntimeException(e);
//        }
//    }
}
