package com.daimler.architecture.model;

import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.common.serialization.Serdes;
import org.apache.kafka.common.serialization.Serializer;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;

import java.util.Properties;

@Configuration
public class UserConfiguration {

    private final Serializer<String> stringSerdes = Serdes.String().serializer();

    @Bean
    @Scope(ConfigurableBeanFactory.SCOPE_SINGLETON)
    public KafkaProducer<String, String> createKafkaProducer() {
        return new KafkaProducer(producerConfig(), stringSerdes, stringSerdes);
    }

    private Properties producerConfig() {
        Properties properties = new Properties();
        properties.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, "localhost:9092");
        properties.put(ProducerConfig.ACKS_CONFIG, "all");
        return properties;
    }
}
