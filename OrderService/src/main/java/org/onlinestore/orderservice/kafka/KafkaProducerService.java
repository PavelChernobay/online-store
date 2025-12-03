package org.onlinestore.orderservice.kafka;

import dto.AnalyticsKafkaEvent;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class KafkaProducerService {

    private final KafkaTemplate<String, AnalyticsKafkaEvent> kafkaTemplate;

    @Value("${spring.kafka.topic.analytics-topic}")
    private String topic;

    public void publishAnalytics(AnalyticsKafkaEvent event) {
        log.info("Сообщение отправлено в kafka {}", event);
        kafkaTemplate.send(topic, event);
    }

}
