package com.upgrad.streams.kafkastreamspoc.station;

import com.upgrad.streams.kafkastreamspoc.model.ComponentView;
import org.apache.kafka.streams.kstream.KStream;
import org.apache.kafka.streams.kstream.KTable;
import org.springframework.cloud.stream.annotation.Input;
import org.springframework.cloud.stream.annotation.Output;

public interface ComponentViewStationBinding {
    String COMPONENT_VIEWS_IN = "cvin";
    String COMPONENT_COUNT_MATERIALIZED_VIEW = "ccmv";
    String COMPONENT_COUNT_OUT = "ccout";
    String COMPONENT_COUNT_IN = "ccin";

    @Input(COMPONENT_VIEWS_IN)
    KStream<String, ComponentView> componentViewsIn();

    @Output(COMPONENT_COUNT_OUT)
    KStream<String, Long> componentCountOut();

    @Input(COMPONENT_COUNT_IN)
    KTable<String, Long> componentCountIn();
}
