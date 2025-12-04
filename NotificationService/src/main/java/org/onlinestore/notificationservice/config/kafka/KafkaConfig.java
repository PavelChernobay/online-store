package org.onlinestore.notificationservice.config.kafka;

import dto.AnalyticsKafkaEvent;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.core.ConsumerFactory;
import org.springframework.kafka.core.DefaultKafkaConsumerFactory;
import org.springframework.kafka.support.serializer.ErrorHandlingDeserializer;
import org.springframework.kafka.support.serializer.JsonDeserializer;

import java.util.HashMap;
import java.util.Map;

/**
 * Конфигурация Kafka Consumer для notification-service.
 * Отвечает за создание фабрики консьюмеров и контейнера слушателей.
 */
@Configuration
public class KafkaConfig {

    @Value("${spring.kafka.bootstrap-servers}")
    private String bootstrapServers;

    /**
     * Создаёт фабрику Kafka-консьюмеров для обработки событий {@link AnalyticsKafkaEvent}.
     *
     * <p>Настройки включают:
     * <ul>
     *     <li>адрес Kafka-брокера</li>
     *     <li>десериализацию ключей через {@link StringDeserializer}</li>
     *     <li>десериализацию значений через {@link ErrorHandlingDeserializer}</li>
     *     <li>использование {@link JsonDeserializer} для преобразования JSON → {@link AnalyticsKafkaEvent}</li>
     *     <li>чтение сообщений с начала в случае отсутствия offset</li>
     * </ul>
     *
     * @return фабрика консьюмеров Kafka
     */
    @Bean
    public ConsumerFactory<String, AnalyticsKafkaEvent> consumerFactory() {
        Map<String, Object> props = new HashMap<>();
        props.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapServers);
        props.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);
        props.put(ErrorHandlingDeserializer.VALUE_DESERIALIZER_CLASS, JsonDeserializer.class);
        props.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, ErrorHandlingDeserializer.class);
        props.put(JsonDeserializer.TRUSTED_PACKAGES, "dto");
        props.put(JsonDeserializer.VALUE_DEFAULT_TYPE, "dto.AnalyticsKafkaEvent");
        props.put(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG, "earliest");
        props.put(JsonDeserializer.USE_TYPE_INFO_HEADERS, false);

        return new DefaultKafkaConsumerFactory<>(props);
    }

    /**
     * Создаёт фабрику контейнеров Kafka Listener для обработки сообщений.
     *
     * <p>Используется всеми методами, помеченными {@code @KafkaListener}.
     *
     * @return фабрика контейнеров Kafka Listener
     */
    @Bean
    public ConcurrentKafkaListenerContainerFactory<String, AnalyticsKafkaEvent> kafkaListenerContainerFactory() {
        ConcurrentKafkaListenerContainerFactory<String, AnalyticsKafkaEvent> factory =
                new ConcurrentKafkaListenerContainerFactory<>();
        factory.setConsumerFactory(consumerFactory());

        return factory;
    }

}
