package org.onlinestore.orderservice.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.onlinestore.orderservice.dto.AnalyticsKafkaEvent;
import org.onlinestore.orderservice.entity.AnalyticsOutbox;
import org.onlinestore.orderservice.entity.EventStatus;
import org.onlinestore.orderservice.entity.Order;
import org.onlinestore.orderservice.kafka.KafkaProducerService;
import org.onlinestore.orderservice.repository.AnalyticsOutboxRepository;
import org.onlinestore.orderservice.service.AnalyticsOutboxService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class AnalyticsOutboxServiceImpl implements AnalyticsOutboxService {

    private final AnalyticsOutboxRepository analyticsOutboxRepository;
    private final KafkaProducerService kafkaProducerService;

    @Transactional
    @Override
    public void createAnalyticsOutbox(Order order) {
        List<AnalyticsOutbox> listAnalyticsOutbox = order.getOrderProducts().stream()
                .map(product -> AnalyticsOutbox.builder()
                        .orderId(order.getId())
                        .userId(order.getUser().getId())
                        .productId(product.getId())
                        .quantity(product.getQuantity())
                        .price(product.getPrice())
                        .sale(product.getSale())
                        .totalPrice(product.getTotalSum())
                        .eventStatus(EventStatus.NEW)
                        .build())
                .toList();
        analyticsOutboxRepository.saveAll(listAnalyticsOutbox);
    }

    @Transactional
    @Override
    public void updateEventStatus(List<AnalyticsOutbox> analyticsOutboxes) {
        analyticsOutboxes.forEach(prod -> prod.setEventStatus(EventStatus.COMPLETED)
        );
    }

    @Override
    public List<AnalyticsOutbox> getAllAnalyticsOutboxNewStatus() {
        return analyticsOutboxRepository.findAllByEventStatus(EventStatus.NEW);
    }

    @Override
    public void processAnalyticsEvents() {
        List<AnalyticsOutbox> allByEventStatus = analyticsOutboxRepository.findAllByEventStatus(EventStatus.NEW);

        List<AnalyticsOutbox> processedEvents = new ArrayList<>();

        for (AnalyticsOutbox event : allByEventStatus) {
            try {
                kafkaProducerService.publishAnalytics(getAnalyticsKafkaProducer(event));
                processedEvents.add(event);
            } catch (Exception e) {
                log.warn("Ошибка при отправке события в kafka: {}", event.getId(), e);
            }
        }

        processedEvents.forEach(proc -> proc.setEventStatus(EventStatus.COMPLETED));
        analyticsOutboxRepository.saveAll(processedEvents);
    }

    private AnalyticsKafkaEvent getAnalyticsKafkaProducer(AnalyticsOutbox analyticsOutbox) {
        return AnalyticsKafkaEvent.builder()
                .productId(analyticsOutbox.getProductId())
                .orderId(analyticsOutbox.getOrderId())
                .userId(analyticsOutbox.getUserId())
                .quantity(analyticsOutbox.getQuantity())
                .price(analyticsOutbox.getPrice())
                .sale(analyticsOutbox.getSale())
                .totalPrice(analyticsOutbox.getTotalPrice())
                .build();
    }

}
