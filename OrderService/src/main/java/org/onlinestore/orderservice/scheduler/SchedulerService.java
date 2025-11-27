package org.onlinestore.orderservice.scheduler;

import lombok.RequiredArgsConstructor;
import org.onlinestore.orderservice.entity.InventoryOutbox;
import org.onlinestore.orderservice.grpc.client.InventoryGrpcClient;
import org.onlinestore.orderservice.service.AnalyticsOutboxService;
import org.onlinestore.orderservice.service.InventoryOutboxService;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.UUID;

@Component
@RequiredArgsConstructor
public class SchedulerService {

    private final InventoryOutboxService inventoryOutboxService;
    private final InventoryGrpcClient inventoryGrpcClient;
    private final AnalyticsOutboxService analyticsOutboxService;

    @Scheduled(fixedDelayString = "${spring.scheduler.inventory-outbox.fix-delay}")
    public void processInventoryScheduler() {
        String traceId = UUID.randomUUID().toString();
        List<InventoryOutbox> newEvents = inventoryOutboxService.getAllInventoryOutboxNewEventsStatus();
        inventoryGrpcClient.updateProductQuantities(newEvents, traceId);
        inventoryOutboxService.updateEventStatus(newEvents);
    }

    @Scheduled(fixedDelayString = "${spring.scheduler.inventory-outbox.fix-delay}")
    public void processAnalyticsScheduler() {
        analyticsOutboxService.processAnalyticsEvents();
    }

}
