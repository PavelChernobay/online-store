package org.onlinestore.orderservice.scheduler;

import lombok.RequiredArgsConstructor;
import org.onlinestore.orderservice.entity.InventoryOutbox;
import org.onlinestore.orderservice.grpc.client.InventoryGrpcClient;
import org.onlinestore.orderservice.service.AnalyticsOutboxService;
import org.onlinestore.orderservice.service.InventoryOutboxService;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class SchedulerService {

    private final InventoryOutboxService inventoryOutboxService;
    private final InventoryGrpcClient inventoryGrpcClient;
    private final AnalyticsOutboxService analyticsOutboxService;

    @Scheduled(fixedDelayString = "${spring.scheduler.inventory-outbox.fix-delay}")
    public void processInventoryScheduler() {
        List<InventoryOutbox> newEvents = inventoryOutboxService.getAllInventoryOutboxNewEventsStatus();
        inventoryGrpcClient.updateProductQuantities(newEvents);
        inventoryOutboxService.updateEventStatus(newEvents);
    }

    @Scheduled(fixedDelayString = "${spring.scheduler.inventory-outbox.fix-delay}")
    public void processAnalyticsScheduler() {
        analyticsOutboxService.processAnalyticsEvents();
    }

}
