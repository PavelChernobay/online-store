package org.onlinestore.orderservice.unit.service.impl;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.onlinestore.orderservice.entity.AnalyticsOutbox;
import org.onlinestore.orderservice.entity.EventStatus;
import org.onlinestore.orderservice.generator.TestDataGenerator;
import org.onlinestore.orderservice.kafka.KafkaProducerService;
import org.onlinestore.orderservice.repository.AnalyticsOutboxRepository;
import org.onlinestore.orderservice.service.impl.AnalyticsOutboxServiceImpl;

import java.util.List;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyList;
import static org.mockito.ArgumentMatchers.argThat;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class AnalyticsOutboxServiceImplTest {

    private AnalyticsOutbox analyticsOutbox;

    @Mock
    private AnalyticsOutboxRepository analyticsOutboxRepository;

    @Mock
    private KafkaProducerService kafkaProducerService;

    @InjectMocks
    private AnalyticsOutboxServiceImpl analyticsOutboxService;

    @BeforeEach
    void setUp() {
        analyticsOutbox = TestDataGenerator.generateAnalyticsOutbox();
    }

    @Test
    void createAnalyticsOutbox() {
        analyticsOutboxService.createAnalyticsOutbox(TestDataGenerator.generateOrder());

        verify(analyticsOutboxRepository, times(1)).saveAll(anyList());
    }

    @Test
    void updateEventStatus() {
        List<AnalyticsOutbox> expected = List.of(analyticsOutbox);

        analyticsOutboxService.updateEventStatus(expected);

        assertEquals(EventStatus.COMPLETED, expected.get(0).getEventStatus());
    }

    @Test
    void getAllAnalyticsOutboxNewStatus() {
        List<AnalyticsOutbox> expected = List.of(analyticsOutbox);

        when(analyticsOutboxRepository.findAllByEventStatus(any())).thenReturn(expected);

        List<AnalyticsOutbox> result = analyticsOutboxService.getAllAnalyticsOutboxNewStatus();

        assertThat(result).isEqualTo(expected);

        verify(analyticsOutboxRepository, times(1)).findAllByEventStatus(any());
    }

    @Test
    void processAnalyticsEvents() {
        AnalyticsOutbox test = TestDataGenerator.generateAnalyticsOutbox();
        AnalyticsOutbox eventFailed = AnalyticsOutbox.builder()
                .id(2L)
                .eventStatus(EventStatus.NEW)
                .build();
        List<AnalyticsOutbox> outbox = List.of(analyticsOutbox, test, eventFailed);

        when(analyticsOutboxRepository.findAllByEventStatus(any(EventStatus.class)))
                .thenReturn(outbox);
        doNothing().when(kafkaProducerService).publishAnalytics(any());
        doThrow(RuntimeException.class).when(kafkaProducerService).publishAnalytics(
                argThat(arg -> arg.id().equals(eventFailed.getId())));

        analyticsOutboxService.processAnalyticsEvents();

        assertEquals(EventStatus.COMPLETED, analyticsOutbox.getEventStatus());
        assertEquals(EventStatus.COMPLETED, test.getEventStatus());
        assertEquals(EventStatus.NEW, eventFailed.getEventStatus());

        verify(analyticsOutboxRepository, times(1)).findAllByEventStatus(any());
        verify(kafkaProducerService, times(3)).publishAnalytics(any());
    }
}