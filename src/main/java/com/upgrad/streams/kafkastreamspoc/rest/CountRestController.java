package com.upgrad.streams.kafkastreamspoc.rest;

import com.upgrad.streams.kafkastreamspoc.station.ComponentViewStationBinding;
import lombok.AllArgsConstructor;
import org.apache.kafka.streams.KeyValue;
import org.apache.kafka.streams.state.KeyValueIterator;
import org.apache.kafka.streams.state.QueryableStoreTypes;
import org.apache.kafka.streams.state.ReadOnlyKeyValueStore;
import org.springframework.cloud.stream.binder.kafka.streams.InteractiveQueryService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
@AllArgsConstructor
public class CountRestController {

    private final InteractiveQueryService queryService;

    @GetMapping("/counts")
    public Map<String, Long> getComponentsViewsCount() {
        Map<String, Long> result = new HashMap<>();
        ReadOnlyKeyValueStore<byte[], Long> store = queryService.getQueryableStore(ComponentViewStationBinding.COMPONENT_COUNT_MATERIALIZED_VIEW, QueryableStoreTypes.keyValueStore());
        KeyValueIterator<byte[], Long> all = store.all();

        while (all.hasNext()) {
            KeyValue<byte[], Long> next = all.next();
            result.put(new String(next.key), next.value);
        }
        return result;
    }
}
