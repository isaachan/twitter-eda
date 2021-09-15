package com.daimler.architecture.database;

import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;

public class UserEventConsumer implements CommandLineRunner {

    public static void main(String args[]) {
        SpringApplication.run(UserEventConsumer.class, args);
    }

    @Override
    public void run(String... args) throws Exception {
    }
}
