package com.daimler.user.database;

import com.daimler.user.model.UserTopicValue;
import com.daimler.user.web.UserController;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.WebApplicationType;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.time.Duration;
import java.util.Arrays;
import java.util.Properties;

@SpringBootApplication
public class UserEventConsumer implements CommandLineRunner {

    @Autowired
    private DBWriter dbWriter;

    public static void main(String args[]) {
        SpringApplication app = new SpringApplication(UserEventConsumer.class);
        app.setWebApplicationType(WebApplicationType.NONE); //<<<<<<<<<
        app.run(args);

//        SpringApplication.run(UserEventConsumer.class, args);
    }

    @Override
    public void run(String... args) throws Exception {
        var consumer = new KafkaConsumer<String, String>(consumerConfig());
        consumer.subscribe(Arrays.asList(UserController.USERS_TOPIC));

        while (true) {
            var records = consumer.poll(Duration.ofMillis(20));
            records.forEach(r -> persist(r));
        }
    }

    private UserTopicValue convertFromJson(String record) {
        try {
            return new ObjectMapper().readValue(record, UserTopicValue.class);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private void persist(ConsumerRecord<String, String>  record) {
        var userTopicValue = convertFromJson(record.value());
        switch(userTopicValue.getAction()) {
            case "create":
                dbWriter.createUser(userTopicValue.getUser());
                break;
            case "update":
                dbWriter.updateUser(userTopicValue.getUser());
                break;
            case "add-follower":
                dbWriter.addFollower(userTopicValue.getTargetId(), userTopicValue.getFollowerId());
                break;
            case "delete-follower":
                dbWriter.deleteFollower(userTopicValue.getTargetId(), userTopicValue.getFollowerId());
                break;
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
