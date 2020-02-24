package com.upgrad.streams.kafkastreamspoc.producer;

import org.springframework.cloud.stream.annotation.Output;
import org.springframework.messaging.MessageChannel;

public interface ComponentViewProducerBinding {

    String COMPONENT_VIEWS_OUT = "cvout";

    @Output(COMPONENT_VIEWS_OUT)
    MessageChannel componentViewsOut();
}
