package org.onlinestore.orderservice.service.impl;

import lombok.RequiredArgsConstructor;
import org.onlinestore.orderservice.entity.EventStatus;
import org.onlinestore.orderservice.entity.InventoryOutbox;
import org.onlinestore.orderservice.repository.InventoryOutboxRepository;
import org.onlinestore.orderservice.service.InventoryOutboxService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class InventoryOutboxServiceImpl implements InventoryOutboxService {

    private final InventoryOutboxRepository inventoryOutboxRepository;

    @Override
    public InventoryOutbox createInventoryOutbox(String productName, int quantity) {
        InventoryOutbox inventoryOutbox = InventoryOutbox.builder()
                .productName(productName)
                .quantity(quantity)
                .eventStatus(EventStatus.NEW)
                .build();

        return inventoryOutboxRepository.save(inventoryOutbox);
    }

    @Override
    public void updateEventStatus(List<InventoryOutbox> inventoryOutboxes) {
        inventoryOutboxes.forEach(prod -> prod.setEventStatus(EventStatus.COMPLETED));
        inventoryOutboxRepository.saveAll(inventoryOutboxes);
    }

    @Override
    public List<InventoryOutbox> getAllInventoryOutboxNewEventsStatus() {
        return inventoryOutboxRepository.findAllByEventStatus(EventStatus.NEW);
    }
}
