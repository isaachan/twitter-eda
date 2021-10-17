package com.daimler.architecture.model;

import com.daimler.tweetcount.TweetsCountStreamTopology;
import org.apache.kafka.common.serialization.Serdes;
import org.apache.kafka.streams.StreamsConfig;
import org.apache.kafka.streams.processor.MockProcessorContext;
//import org.apache.kafka.streams.processor.api.MockProcessorContext;
import org.apache.kafka.streams.processor.ProcessorContext;
import org.apache.kafka.streams.processor.StateStoreContext;
import org.apache.kafka.streams.state.KeyValueStore;
import org.apache.kafka.streams.state.Stores;
import org.junit.jupiter.api.BeforeEach;

import java.util.Properties;

public class TweetsCountTransformerTests {

    MockProcessorContext processorContext;

    @BeforeEach
    public void setUp() {
        var props = new Properties();
        props.put(StreamsConfig.APPLICATION_ID_CONFIG, "test");
        props.put(StreamsConfig.BOOTSTRAP_SERVERS_CONFIG, "dummy:1234");
        processorContext = new MockProcessorContext(props);

        var storeSupplier = Stores.inMemoryKeyValueStore(TweetsCountStreamTopology.TWEET_COUNT_STATE_STORE);
        var store = Stores.keyValueStoreBuilder(storeSupplier, Serdes.Long(), Serdes.Long()).build();

        store.init(processorContext, store);
        processorContext.register(store, null);
    }
}
