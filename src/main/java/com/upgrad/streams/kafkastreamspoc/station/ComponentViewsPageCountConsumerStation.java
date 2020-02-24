package com.upgrad.streams.kafkastreamspoc.station;

import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.streams.kstream.KTable;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.cloud.stream.annotation.Input;
import org.springframework.cloud.stream.annotation.StreamListener;

import static com.upgrad.streams.kafkastreamspoc.station.ComponentViewStationBinding.COMPONENT_COUNT_IN;

@Slf4j
@EnableBinding(ComponentViewStationBinding.class)
public class ComponentViewsPageCountConsumerStation {

    @StreamListener
    public void process(@Input(COMPONENT_COUNT_IN) KTable<byte[], Long> componentViewesCounts) {
        componentViewesCounts.toStream()
                .foreach((componentName, count) -> log.info("Component: {}, count: {}", new String(componentName), count));
    }
}
