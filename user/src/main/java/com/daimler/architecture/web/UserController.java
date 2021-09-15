package com.daimler.architecture.web;

import com.daimler.architecture.model.User;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
public class UserController {

    @Autowired
    private KafkaProducer<String, String> kafkaProducer;

    public static final String USERS_TOPIC = "users_topic";

    @PostMapping("/users/")
    public void newUser(@RequestBody User u) {
        kafkaProducer.send(new ProducerRecord<>(
                USERS_TOPIC,
                String.valueOf(u.getId()),
                convertToJson(new UserTopicValue("create", u))));
    }

    @PutMapping("/users/")
    public void updateUser(@RequestBody User u) {
        kafkaProducer.send(new ProducerRecord<>(
                USERS_TOPIC,
                String.valueOf(u.getId()),
                convertToJson(new UserTopicValue("update", u))));
    }

    @PostMapping("/users/{id}/followers")
    public void addFollower(@PathVariable Long me, @RequestBody User follower) {
        kafkaProducer.send(new ProducerRecord<>(
                USERS_TOPIC,
                String.valueOf(me),
                convertToJson(new UserTopicValue("add-follower", me, follower.getId()))
        ));
    }

    @DeleteMapping("/users/{id}/followers/{followerId}")
    public void deleteFollower(@PathVariable Long me, @PathVariable Long followerId) {
        kafkaProducer.send(new ProducerRecord<>(
                USERS_TOPIC,
                String.valueOf(me),
                convertToJson(new UserTopicValue("delete-follower", me, followerId))
        ));
    }

    @GetMapping("/users/{id}")
    public User findBy(@PathVariable("id") Long id) {
        return null;
    }

    private String convertToJson(Object object) {
        try {
            return new ObjectMapper().writeValueAsString(object);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}

