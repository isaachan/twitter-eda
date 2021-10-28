package com.daimler.architecture.model;

import com.daimler.tweetcount.TweetsCountStreamTopology;
import com.daimler.tweetcount.model.UserNotFoundException;
import org.apache.kafka.common.serialization.Serdes;
import org.apache.kafka.streams.StreamsConfig;
import org.apache.kafka.streams.TestInputTopic;
import org.apache.kafka.streams.TopologyTestDriver;
import org.apache.kafka.streams.errors.StreamsException;
import org.apache.kafka.streams.state.KeyValueStore;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Properties;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class IntegratedTests {

    private TopologyTestDriver testDriver;
    private TestInputTopic<String, String> retweetInputTopic;
    private TestInputTopic<String, String> tweetInputTopic;
    private TestInputTopic<String, String> userInputTopic;

    String create_event_1000_by_sender1 = "{\"content\":{\"id\":1000,\"content\":\"This is a tweet.\",\"sender\":1,\"timeline\":1634021617947},\"action\":\"create_action\",\"sender\":1}";
    String delete_event_1000_by_sender1 = "{\"content\":{\"id\":1000,\"content\":\"This is a tweet.\",\"sender\":1,\"timeline\":1634021617947},\"action\":\"delete_action\",\"sender\":1}";
    String create_event_1001_by_sender2 = "{\"content\":{\"id\":1001,\"content\":\"This is a tweet.\",\"sender\":1,\"timeline\":1634021621547},\"action\":\"create_action\",\"sender\":2}";
    String create_event_1002_by_sender2 = "{\"content\":{\"id\":1002,\"content\":\"This is a tweet.\",\"sender\":1,\"timeline\":1634108021547},\"action\":\"create_action\",\"sender\":2}";
    String create_event_1003_by_sender2 = "{\"content\":{\"id\":1003,\"content\":\"This is a tweet.\",\"sender\":1,\"timeline\":1634194421547},\"action\":\"create_action\",\"sender\":2}";

    @BeforeEach
    public void setUp() {
        var topology = TweetsCountStreamTopology.build();

        Properties props = new Properties();
        props.put(StreamsConfig.APPLICATION_ID_CONFIG, "test");
        props.put(StreamsConfig.BOOTSTRAP_SERVERS_CONFIG, "dummy:1234");

        testDriver = new TopologyTestDriver(topology, props);
        tweetInputTopic = testDriver.createInputTopic("tweets_topic", Serdes.String().serializer(), Serdes.String().serializer());
        retweetInputTopic = testDriver.createInputTopic("retweets_topic", Serdes.String().serializer(), Serdes.String().serializer());
        userInputTopic = testDriver.createInputTopic("users_topic", Serdes.String().serializer(), Serdes.String().serializer());
    }

    @Test
    public void should_increase_count_when_create_tweets() {
        userInputTopic.pipeInput("1", "");
        userInputTopic.pipeInput("2", "");

        tweetInputTopic.pipeInput("1", create_event_1000_by_sender1);
        tweetInputTopic.pipeInput("2", create_event_1001_by_sender2);
        tweetInputTopic.pipeInput("2", create_event_1002_by_sender2);
        tweetInputTopic.pipeInput("2", create_event_1003_by_sender2);

        var stateStore = testDriver.getKeyValueStore("tweetCountStateStore");
        assertEquals(1L, stateStore.get(1L));
        assertEquals(3L, stateStore.get(2L));
    }

    @Test
    public void creating_user_before_update_counts() {
        try {
            tweetInputTopic.pipeInput("1", create_event_1000_by_sender1);
        } catch (StreamsException e) {
            assertEquals(UserNotFoundException.class, e.getCause().getClass());
        }
        
        userInputTopic.pipeInput("1", "");
        tweetInputTopic.pipeInput("1", create_event_1000_by_sender1);
    }

    @AfterEach
    public void cleanDown() {
        testDriver.close();
    }
}
