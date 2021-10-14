package com.daimler.tweet.model;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.kafka.common.serialization.Deserializer;
import org.apache.kafka.common.serialization.Serde;
import org.apache.kafka.common.serialization.Serializer;

import java.io.IOException;

public class TimelineSerdes {

    public static Serde<Timeline> build() {

        var mapper = new ObjectMapper();

        return new Serde<>() {
            @Override
            public Serializer<Timeline> serializer() {
                return (topic, timeline) -> {
                    try {
                        return mapper.writeValueAsBytes(timeline);
                    } catch (JsonProcessingException e) {
                        throw new RuntimeException(e);
                    }
                };
            }

            @Override
            public Deserializer<Timeline> deserializer() {
                return (topic, data) -> {
                    try {
                        return mapper.readValue(data, Timeline.class);
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                };
            }
        };
    }
}
