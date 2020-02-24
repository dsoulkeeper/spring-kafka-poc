package com.upgrad.streams.kafkastreamspoc.station;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.upgrad.streams.kafkastreamspoc.model.PageView;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.streams.KeyValue;
import org.apache.kafka.streams.kstream.KStream;
import org.apache.kafka.streams.kstream.Materialized;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.cloud.stream.annotation.Input;
import org.springframework.cloud.stream.annotation.StreamListener;
import org.springframework.messaging.handler.annotation.SendTo;

import static com.upgrad.streams.kafkastreamspoc.station.PageViewStationBinding.PAGE_COUNT_OUT;

@Slf4j
@EnableBinding(PageViewStationBinding.class)
public class PageViewsPageCountProducerStation {

    private final ObjectMapper objectMapper = new ObjectMapper();

    @StreamListener
    @SendTo(PAGE_COUNT_OUT)
    public KStream<byte[], Long> process(@Input(PageViewStationBinding.PAGE_VIEWS_IN) KStream<byte[], byte[]> pageViewKStream) {
        //  KStream<String, PageView>
        return pageViewKStream
                .filter((userId, pageView) -> getValue(pageView, true).getDuration() > 10)
                .map((userId, pageView) -> new KeyValue<>(getValue(pageView, false).getPage().getBytes(), "0".getBytes()))
                .groupByKey()
                // This will give list of ktables analyzed per hour
//                .windowedBy(TimeWindows.of(Duration.ofHours(1)))

                // We will have to persist this count somewhere so that we can increment it
                // Kafka already provides this via Materialized Views
                // This view can be queries easily.
                // This count would return KTable
                .count(Materialized.as(PageViewStationBinding.PAGE_COUNT_MATERIALIZED_VIEW))
                .toStream();

        // KTable can further be forwarded as another stream.
        // So this small piece of code can actually become a transformation station of big pipeline
    }

    private String getKey(byte[] bytes) {
        return new String(bytes);
    }

    @SneakyThrows
    private PageView getValue(byte[] bytes, boolean print) {
        PageView pageView = objectMapper.readValue(new String(bytes), PageView.class);
        if (print) {
            log.info("Received: " + objectMapper.writeValueAsString(pageView));
        }
        return pageView;
    }
}
