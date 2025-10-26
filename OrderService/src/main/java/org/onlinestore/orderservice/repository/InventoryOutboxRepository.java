package org.onlinestore.orderservice.repository;

import org.onlinestore.orderservice.entity.EventStatus;
import org.onlinestore.orderservice.entity.InventoryOutbox;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface InventoryOutboxRepository extends JpaRepository<InventoryOutbox, Long> {

    List<InventoryOutbox> findAllByEventStatus(EventStatus eventStatus);

}
