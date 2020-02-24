package com.upgrad.streams.kafkastreamspoc.producer;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.upgrad.streams.kafkastreamspoc.model.PageView;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;
import java.util.Random;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

@Service
@EnableBinding(PageViewProducerBinding.class)
@Slf4j
public class PageViewProducerService implements ApplicationRunner {

    private final MessageChannel pageViewsOut;

    private final List<Long> userIds = Arrays.asList(1L, 2L, 3L, 4L, 5L, 6L, 7L, 8L, 9L, 10L, 11L, 12L, 13L, 14L, 15L);
    private final List<String> pages = Arrays.asList("home", "Bridgelabz", "MS Computer Science", "MS Big Data", "Global MBA", "MBA");
    private final Random random = new Random();
    private final ObjectMapper objectMapper = new ObjectMapper();

    public PageViewProducerService(PageViewProducerBinding pageViewProducerBinding) {
        this.pageViewsOut = pageViewProducerBinding.pageViewsOut();
    }

    @Override
    public void run(ApplicationArguments args) throws Exception {
        Executors.newScheduledThreadPool(1).scheduleAtFixedRate(this::sendMessage, 1, 300, TimeUnit.MILLISECONDS);
    }

    @SneakyThrows
    private void sendMessage() {
        PageView randomPageView = getRandomPageView();
        String payLoad = objectMapper.writeValueAsString(randomPageView);
        Message<byte[]> pageViewMessage = MessageBuilder
                .withPayload(payLoad.getBytes())
                .setHeader(KafkaHeaders.MESSAGE_KEY, randomPageView.getUserId().toString().getBytes())
                .build();
        try {
            this.pageViewsOut.send(pageViewMessage);
            log.info("Sent {}", payLoad);
        } catch (Exception e) {
            log.error(e.getMessage(), e);
        }
    }

    private PageView getRandomPageView() {
        return PageView.builder()
                .userId(userIds.get(random.nextInt(userIds.size())))
                .page(pages.get(random.nextInt(pages.size())))
                .duration(random.nextInt(1000))
                .build();
    }
}
