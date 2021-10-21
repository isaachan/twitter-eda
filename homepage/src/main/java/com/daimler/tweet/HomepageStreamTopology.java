package com.daimler.tweet;

import org.apache.kafka.streams.StreamsBuilder;
import org.apache.kafka.streams.Topology;

public class HomepageStreamTopology {

    public static Topology build() {
        var builder = new StreamsBuilder();
        builder.stream("");

        return builder.build();
    }

}
