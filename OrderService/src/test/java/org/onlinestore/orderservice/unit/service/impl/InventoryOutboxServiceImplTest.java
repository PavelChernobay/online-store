package org.onlinestore.orderservice.unit.service.impl;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.onlinestore.orderservice.entity.EventStatus;
import org.onlinestore.orderservice.entity.InventoryOutbox;
import org.onlinestore.orderservice.generator.TestDataGenerator;
import org.onlinestore.orderservice.repository.InventoryOutboxRepository;
import org.onlinestore.orderservice.service.impl.InventoryOutboxServiceImpl;

import java.util.List;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyList;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class InventoryOutboxServiceImplTest {

    private InventoryOutbox inventoryOutbox;

    @Mock
    private InventoryOutboxRepository inventoryOutboxRepository;

    @InjectMocks
    private InventoryOutboxServiceImpl inventoryOutboxService;

    @BeforeEach
    void setUp() {
        inventoryOutbox = TestDataGenerator.generateInventoryOutbox();
    }

    @Test
    void testCreateInventoryOutboxSuccess() {
        when(inventoryOutboxRepository.save(any(InventoryOutbox.class))).thenReturn(inventoryOutbox);

        InventoryOutbox result = inventoryOutboxService.createInventoryOutbox(
                TestDataGenerator.PRODUCT_NAME, TestDataGenerator.QUANTITY);

        assertThat(result).isEqualTo(inventoryOutbox);

        verify(inventoryOutboxRepository, times(1)).save(any(InventoryOutbox.class));
    }

    @Test
    void testUpdateEventStatusSuccess() {
        InventoryOutbox test = TestDataGenerator.generateInventoryOutbox();
        List<InventoryOutbox> outboxes = List.of(inventoryOutbox, test);

        inventoryOutboxService.updateEventStatus(outboxes);

        assertEquals(EventStatus.COMPLETED, inventoryOutbox.getEventStatus());
        assertEquals(EventStatus.COMPLETED, test.getEventStatus());

        verify(inventoryOutboxRepository, times(1)).saveAll(anyList());
    }

    @Test
    void testGetAllInventoryOutboxNewEventsStatusSuccess() {
        InventoryOutbox test = TestDataGenerator.generateInventoryOutbox();
        List<InventoryOutbox> outboxes = List.of(inventoryOutbox, test);

        when(inventoryOutboxRepository.findAllByEventStatus(any(EventStatus.class)))
                .thenReturn(outboxes);

        List<InventoryOutbox> result = inventoryOutboxService.getAllInventoryOutboxNewEventsStatus();

        assertEquals(2, result.size());
        assertEquals(EventStatus.NEW, result.get(0).getEventStatus());
        assertEquals(EventStatus.NEW, result.get(1).getEventStatus());

        verify(inventoryOutboxRepository, times(1))
                .findAllByEventStatus(any(EventStatus.class));
    }
}