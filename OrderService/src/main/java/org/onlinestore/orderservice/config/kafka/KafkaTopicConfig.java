package org.onlinestore.orderservice.config.kafka;

import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Конфигурация топиков Kafka для OrderService.
 * <p>
 * Создает необходимые топики Kafka с заданным количеством партиций и фактором репликации.
 */
@Configuration
public class KafkaTopicConfig {

    @Value("${spring.kafka.topic.analytics-topic}")
    private String analyticsTopic;

    @Value("${spring.kafka.partition.count}")
    private int partitionCount;

    @Value("${spring.kafka.streams.replication-factor}")
    private short replicationFactor;

    /**
     * Создает топик для аналитики заказов.
     * <p>
     * Использует настройки:
     * <ul>
     *     <li>Название топика (analyticsTopic)</li>
     *     <li>Количество партиций (partitionCount)</li>
     *     <li>Фактор репликации (replicationFactor)</li>
     * </ul>
     *
     * @return {@link NewTopic} для топика аналитики заказов
     */
    @Bean
    public NewTopic orderTopic() {
        return new NewTopic(analyticsTopic, partitionCount, replicationFactor);
    }

}
