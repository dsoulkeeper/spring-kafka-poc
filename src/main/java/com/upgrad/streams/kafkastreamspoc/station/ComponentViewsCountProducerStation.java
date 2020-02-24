package com.upgrad.streams.kafkastreamspoc.station;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.upgrad.streams.kafkastreamspoc.model.ComponentView;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.streams.KeyValue;
import org.apache.kafka.streams.kstream.KStream;
import org.apache.kafka.streams.kstream.Materialized;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.cloud.stream.annotation.Input;
import org.springframework.cloud.stream.annotation.StreamListener;
import org.springframework.messaging.handler.annotation.SendTo;

import static com.upgrad.streams.kafkastreamspoc.station.ComponentViewStationBinding.COMPONENT_COUNT_OUT;

@Slf4j
@EnableBinding(ComponentViewStationBinding.class)
public class ComponentViewsCountProducerStation {

    private final ObjectMapper objectMapper = new ObjectMapper();

    @StreamListener
    @SendTo(COMPONENT_COUNT_OUT)
    public KStream<byte[], Long> process(@Input(ComponentViewStationBinding.COMPONENT_VIEWS_IN) KStream<byte[], byte[]> componentViewKStream) {
        return componentViewKStream
                .filter((userId, componentView) -> getValue(componentView, true).getDuration() > 10)
                .map((userId, componentView) -> new KeyValue<>(getValue(componentView, false).getComponent().getBytes(), "0".getBytes()))
                .groupByKey()
                // This will give list of ktables analyzed per hour
//                .windowedBy(TimeWindows.of(Duration.ofHours(1)))

                // We will have to persist this count somewhere so that we can increment it
                // Kafka already provides this via Materialized Views
                // This view can be queries easily.
                // This count would return KTable
                .count(Materialized.as(ComponentViewStationBinding.COMPONENT_COUNT_MATERIALIZED_VIEW))
                .toStream();

        // KTable can further be forwarded as another stream.
        // So this small piece of code can actually become a transformation station of big pipeline
    }

    @SneakyThrows
    private ComponentView getValue(byte[] bytes, boolean print) {
        ComponentView componentView = objectMapper.readValue(new String(bytes), ComponentView.class);
        if (print) {
            log.info("Received: " + objectMapper.writeValueAsString(componentView));
        }
        return componentView;
    }
}
