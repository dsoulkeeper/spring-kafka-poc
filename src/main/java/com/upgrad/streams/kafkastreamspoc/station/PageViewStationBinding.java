package com.upgrad.streams.kafkastreamspoc.station;

import com.upgrad.streams.kafkastreamspoc.model.PageView;
import org.apache.kafka.streams.kstream.KStream;
import org.apache.kafka.streams.kstream.KTable;
import org.springframework.cloud.stream.annotation.Input;
import org.springframework.cloud.stream.annotation.Output;

public interface PageViewStationBinding {
    String PAGE_VIEWS_IN = "pvin";
    String PAGE_COUNT_MATERIALIZED_VIEW = "pcmv";
    String PAGE_COUNT_OUT = "pcout";
    String PAGE_COUNT_IN = "pcin";

    @Input(PAGE_VIEWS_IN)
    KStream<String, PageView> pageViewsIn();

    @Output(PAGE_COUNT_OUT)
    KStream<String, Long> pageCountOut();

    @Input(PAGE_COUNT_IN)
    KTable<String, Long> pageCountIn();
}
