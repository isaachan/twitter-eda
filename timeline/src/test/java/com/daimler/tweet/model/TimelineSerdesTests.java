package com.daimler.tweet.model;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.kafka.common.serialization.Deserializer;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class TimelineSerdesTests {

    String event_2021_10_12_3pm = "{\"content\":{\"id\":1000,\"content\":\"This is a tweet.\",\"sender\":1,\"timeline\":1634021617947},\"action\":\"create_action\",\"sender\":1}";

    @Test
    public void test() throws JsonProcessingException {
        var timeline = new Timeline("1");
        timeline.merge(event_2021_10_12_3pm);
        var serdes = TimelineSerdes.build();
        var data = serdes.serializer().serialize("", timeline);
        var rebuildTimeline = serdes.deserializer().deserialize("", data);

        var tweet = rebuildTimeline.get("2021-10-12").get(0);
        assertEquals(1000, tweet.getId());
    }
}
