package com.daimler.architecture.model;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
public class UserController {

    @Autowired
    private KafkaProducer<String, String> kafkaProducer;

    private static final String USERS_TOPIC = "users_topic";

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

