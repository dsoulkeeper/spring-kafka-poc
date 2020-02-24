package com.upgrad.streams.kafkastreamspoc.station;

import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.streams.kstream.KTable;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.cloud.stream.annotation.Input;
import org.springframework.cloud.stream.annotation.StreamListener;

import static com.upgrad.streams.kafkastreamspoc.station.PageViewStationBinding.PAGE_COUNT_IN;

@Slf4j
@EnableBinding(PageViewStationBinding.class)
public class PageViewsPageCountConsumerStation {

    @StreamListener
    public void process(@Input(PAGE_COUNT_IN) KTable<byte[], Long> pageCounts) {
        pageCounts.toStream()
                .foreach((pageName, count) -> log.info("Page: {}, count: {}", new String(pageName), count));
    }
}
