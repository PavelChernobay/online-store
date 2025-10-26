package org.onlinestore.notificationservice.kafka;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.onlinestore.notificationservice.dto.AnalyticsKafkaEvent;
import org.onlinestore.notificationservice.service.OrderService;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class KafkaConsumerService {

    private final OrderService orderService;

    @KafkaListener(topics = "${spring.kafka.topic.analytics-topic}", groupId = "${spring.kafka.consumer.group-id}")
    public void consumeAnalytics(AnalyticsKafkaEvent consumer) {
        log.info("Сообщение обработано в kafka");
        orderService.createOrder(consumer);
    }

}
