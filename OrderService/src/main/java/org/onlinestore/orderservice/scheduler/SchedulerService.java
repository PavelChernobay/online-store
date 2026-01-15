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

/**
 * Планировщик задач для обработки событий Outbox.
 * <p>
 * Содержит два планировщика:
 * <ul>
 *     <li>Обработка событий инвентаризации (InventoryOutbox)</li>
 *     <li>Обработка аналитических событий (AnalyticsOutbox)</li>
 * </ul>
 */
@Component
@RequiredArgsConstructor
public class SchedulerService {

    private final InventoryOutboxService inventoryOutboxService;
    private final InventoryGrpcClient inventoryGrpcClient;
    private final AnalyticsOutboxService analyticsOutboxService;

    /**
     * Планировщик для обработки событий изменения количества товаров.
     * <p>
     * 1. Генерирует traceId.<br>
     * 2. Получает новые события InventoryOutbox.<br>
     * 3. Передаёт их в inventory-service через gRPC.<br>
     * 4. Обновляет статус обработанных событий.
     * </p>
     *
     * @see InventoryOutboxService#getAllInventoryOutboxNewEventsStatus()
     * @see InventoryGrpcClient#updateProductQuantities(List, String)
     * @see InventoryOutboxService#updateEventStatus(List)
     */
    @Scheduled(fixedDelayString = "${spring.scheduler.inventory-outbox.fix-delay}")
    public void processInventoryScheduler() {
        String traceId = UUID.randomUUID().toString();
        List<InventoryOutbox> newEvents = inventoryOutboxService.getAllInventoryOutboxNewEventsStatus();
        inventoryGrpcClient.updateProductQuantities(newEvents, traceId);
        inventoryOutboxService.updateEventStatus(newEvents);
    }

    /**
     * Планировщик обработки аналитических событий.
     * <p>
     * Вызывает сервис для публикации и обновления статусов AnalyticsOutbox.
     * </p>
     *
     * @see AnalyticsOutboxService#processAnalyticsEvents()
     */
    @Scheduled(fixedDelayString = "${spring.scheduler.inventory-outbox.fix-delay}")
    public void processAnalyticsScheduler() {
        analyticsOutboxService.processAnalyticsEvents();
    }

}