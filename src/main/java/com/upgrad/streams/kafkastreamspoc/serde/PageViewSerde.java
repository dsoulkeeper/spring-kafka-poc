package com.upgrad.streams.kafkastreamspoc.serde;

import com.upgrad.streams.kafkastreamspoc.model.PageView;
import org.springframework.kafka.support.serializer.JsonSerde;

public class PageViewSerde extends JsonSerde<PageView> {
}
