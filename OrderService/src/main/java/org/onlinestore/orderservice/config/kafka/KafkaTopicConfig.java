package org.onlinestore.orderservice.config.kafka;

import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class KafkaTopicConfig {

    @Value("${spring.kafka.topic.analytics-topic}")
    private String analyticsTopic;

    @Value("${spring.kafka.partition.count}")
    private int partitionCount;

    @Value("${spring.kafka.streams.replication-factor}")
    private short replicationFactor;

    @Bean
    public NewTopic orderTopic() {
        return new NewTopic(analyticsTopic, partitionCount, replicationFactor);
    }

}
