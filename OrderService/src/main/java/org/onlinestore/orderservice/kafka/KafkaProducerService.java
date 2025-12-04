package org.onlinestore.orderservice.kafka;

import dto.AnalyticsKafkaEvent;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

/**
 * Сервис для отправки аналитических событий в Kafka.
 * <p>
 * Используется для публикации {@link AnalyticsKafkaEvent} в указанный топик.
 * Включает логирование отправки сообщений.
 */
@Service
@RequiredArgsConstructor
@Slf4j
public class KafkaProducerService {

    /**
     * KafkaTemplate для отправки сообщений.
     */
    private final KafkaTemplate<String, AnalyticsKafkaEvent> kafkaTemplate;

    /**
     * Название Kafka топика, в который публикуются события.
     * Берется из application.properties / application.yaml.
     */
    @Value("${spring.kafka.topic.analytics-topic}")
    private String topic;

    /**
     * Публикует аналитическое событие в Kafka.
     *
     * @param event событие {@link AnalyticsKafkaEvent}, которое нужно отправить
     */
    public void publishAnalytics(AnalyticsKafkaEvent event) {
        log.info("Сообщение отправлено в Kafka {}", event);
        kafkaTemplate.send(topic, event);
    }

}