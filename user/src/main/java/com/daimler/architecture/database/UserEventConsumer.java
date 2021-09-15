package com.daimler.architecture.database;

import com.daimler.architecture.model.User;
import com.daimler.architecture.model.UserTopicValue;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;

import java.time.Duration;
import java.util.Properties;

public class UserEventConsumer implements CommandLineRunner {

    @Autowired
    private DBWriter dbWriter;

    public static void main(String args[]) {
        SpringApplication.run(UserEventConsumer.class, args);
    }

    @Override
    public void run(String... args) throws Exception {
        var consumer = new KafkaConsumer<String, String>(consumerConfig());
        var records = consumer.poll(Duration.ofMillis(20));
        for (var record : records) {
            var userTopicValue = convertFromJson(record.value());
            switch(userTopicValue.getAction()) {
                case "create":
                    dbWriter.createUser(userTopicValue.getContent());
                    break;
                case "update":
                    dbWriter.updateUser(userTopicValue.getContent());
                    break;
                case "add-follower":
                    dbWriter.addFollower(userTopicValue.getTargetId(), userTopicValue.getFollowerId());
                    break;
                case "delete-follower":
                    dbWriter.deleteFollower(userTopicValue.getTargetId(), userTopicValue.getFollowerId());
                    break;
            }
        }
    }

    private UserTopicValue convertFromJson(String record) {
        try {
            return new ObjectMapper().readValue(record, UserTopicValue.class);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private Properties consumerConfig() {
        Properties props = new Properties();
        props.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, "localhost:9092");
        props.put(ConsumerConfig.GROUP_ID_CONFIG, "user-event");
        props.put(ConsumerConfig.CLIENT_ID_CONFIG, "user-event");
        props.put(ConsumerConfig.ENABLE_AUTO_COMMIT_CONFIG, "true");
        props.put(ConsumerConfig.AUTO_COMMIT_INTERVAL_MS_CONFIG, "1000");
        props.put(ConsumerConfig.SESSION_TIMEOUT_MS_CONFIG, "30000");
        props.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);
        props.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);
        props.put(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG, "earliest");
        return props;
    }
}
