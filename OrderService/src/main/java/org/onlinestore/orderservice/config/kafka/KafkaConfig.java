package org.onlinestore.orderservice.config.kafka;

import dto.AnalyticsKafkaEvent;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.common.serialization.StringSerializer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.core.DefaultKafkaProducerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.core.ProducerFactory;
import org.springframework.kafka.support.serializer.JsonSerializer;

import java.util.HashMap;
import java.util.Map;

/**
 * Конфигурация Kafka для OrderService.
 * <p>
 * Настраивает продюсера Kafka для отправки сообщений типа {@link AnalyticsKafkaEvent}.
 */
@Configuration
public class KafkaConfig {

    @Value("${spring.kafka.bootstrap-servers}")
    private String bootstrapServers;

    /**
     * Создает фабрику продюсеров Kafka.
     * <p>
     * Конфигурирует:
     * <ul>
     *     <li>Сервер Kafka (bootstrapServers)</li>
     *     <li>Сериализацию ключей (StringSerializer)</li>
     *     <li>Сериализацию значений (JsonSerializer)</li>
     * </ul>
     *
     * @return {@link ProducerFactory} для сообщений {@link AnalyticsKafkaEvent}
     */
    @Bean
    public ProducerFactory<String, AnalyticsKafkaEvent> producerFactory() {
        Map<String, Object> configProps = new HashMap<>();
        configProps.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapServers);
        configProps.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class);
        configProps.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, JsonSerializer.class);
        return new DefaultKafkaProducerFactory<>(configProps);
    }

    /**
     * Создает {@link KafkaTemplate} для отправки сообщений в Kafka.
     *
     * @return {@link KafkaTemplate} для сообщений {@link AnalyticsKafkaEvent}
     */
    @Bean
    public KafkaTemplate<String, AnalyticsKafkaEvent> kafkaTemplate() {
        return new KafkaTemplate<>(producerFactory());
    }

}
