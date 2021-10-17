package com.daimler.architecture.model;

import com.daimler.tweetcount.TweetsCountStreamTopology;
import com.daimler.tweetcount.TweetsCountTransformer;
import org.apache.kafka.common.serialization.Serdes;
import org.apache.kafka.streams.KeyValue;
import org.apache.kafka.streams.StreamsConfig;
import org.apache.kafka.streams.processor.MockProcessorContext;
//import org.apache.kafka.streams.processor.api.MockProcessorContext;
import org.apache.kafka.streams.state.Stores;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Properties;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class TweetsCountTransformerTests {

    MockProcessorContext processorContext;

    public static final String STORE_NAME = TweetsCountStreamTopology.TWEET_COUNT_STATE_STORE;

    String create_event_1000_by_sender1 = "{\"content\":{\"id\":1000,\"content\":\"This is a tweet.\",\"sender\":1,\"timeline\":1634021617947},\"action\":\"create_action\",\"sender\":1}";
    String delete_event_1000_by_sender1 = "{\"content\":{\"id\":1000,\"content\":\"This is a tweet.\",\"sender\":1,\"timeline\":1634021617947},\"action\":\"delete_action\",\"sender\":1}";
    String create_event_1001_by_sender2 = "{\"content\":{\"id\":1001,\"content\":\"This is a tweet.\",\"sender\":1,\"timeline\":1634021621547},\"action\":\"create_action\",\"sender\":2}";
    String create_event_1002_by_sender2 = "{\"content\":{\"id\":1002,\"content\":\"This is a tweet.\",\"sender\":1,\"timeline\":1634108021547},\"action\":\"create_action\",\"sender\":2}";
    String create_event_1003_by_sender2 = "{\"content\":{\"id\":1003,\"content\":\"This is a tweet.\",\"sender\":1,\"timeline\":1634194421547},\"action\":\"create_action\",\"sender\":2}";
    
    @BeforeEach
    public void setUp() {
        var props = new Properties();
        props.put(StreamsConfig.APPLICATION_ID_CONFIG, "test");
        props.put(StreamsConfig.BOOTSTRAP_SERVERS_CONFIG, "dummy:1234");
        processorContext = new MockProcessorContext(props);

        var storeSupplier = Stores.inMemoryKeyValueStore(STORE_NAME);
        var store = Stores
                .keyValueStoreBuilder(storeSupplier, Serdes.Long(), Serdes.Long())
                .withLoggingDisabled()
                .build();

        // TODO Use api.ProcessContext to replace it.
        store.init(processorContext, store);
        processorContext.register(store, null);
    }

    @Test
    public void update_count_by_tweets() {
        var transformer = new TweetsCountTransformer(STORE_NAME);
        transformer.init(processorContext);

        assertTransformerResult(1L, 1L, transformer.transform("1000", create_event_1000_by_sender1));
        assertTransformerResult(1L, 0L, transformer.transform("1000", delete_event_1000_by_sender1));
        assertTransformerResult(2L, 1L, transformer.transform("1000", create_event_1001_by_sender2));
        assertTransformerResult(2L, 2L, transformer.transform("1000", create_event_1002_by_sender2));
        assertTransformerResult(2L, 3L, transformer.transform("1000", create_event_1003_by_sender2));
    }

    private static void assertTransformerResult(Long expectedSender, Long expectedCount, KeyValue<Long, Long> kv) {
        assertEquals(expectedSender, kv.key);
        assertEquals(expectedCount, kv.value);
    }
}
