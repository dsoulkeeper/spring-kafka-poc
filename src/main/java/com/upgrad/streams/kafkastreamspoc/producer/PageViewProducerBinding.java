package com.upgrad.streams.kafkastreamspoc.producer;

import org.springframework.cloud.stream.annotation.Output;
import org.springframework.messaging.MessageChannel;

public interface PageViewProducerBinding {

    String PAGE_VIEWS_OUT = "pvout";

    @Output(PAGE_VIEWS_OUT)
    MessageChannel pageViewsOut();
}
