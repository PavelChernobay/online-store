package org.onlinestore.orderservice.service;

import org.onlinestore.orderservice.entity.InventoryOutbox;

import java.util.List;

public interface InventoryOutboxService {

    InventoryOutbox createInventoryOutbox(String productName, int quantity);

    void updateEventStatus(List<InventoryOutbox> inventoryOutboxes);

    List<InventoryOutbox> getAllInventoryOutboxNewEventsStatus();

}
