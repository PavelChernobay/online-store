package org.onlinestore.orderservice.grpc.client;

import io.grpc.StatusRuntimeException;
import lombok.extern.slf4j.Slf4j;
import net.devh.boot.grpc.client.inject.GrpcClient;
import org.onlinestore.common.grpc.InventoryServiceGrpc;
import org.onlinestore.common.grpc.ProductBatchGrpcRequest;
import org.onlinestore.common.grpc.ProductBatchGrpcResponse;
import org.onlinestore.common.grpc.ProductGrpcRequest;
import org.onlinestore.common.grpc.ProductGrpcResponse;
import org.onlinestore.common.grpc.ProductQuantityBatch;
import org.onlinestore.common.grpc.ProductQuantityRequest;
import org.onlinestore.orderservice.entity.InventoryOutbox;
import org.onlinestore.orderservice.exception.ProductNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * gRPC клиент для взаимодействия с Inventory Service.
 * <p>
 * Используется для получения информации о продуктах, проверки наличия на складе
 * и обновления количества продуктов после создания заказа.
 * <p>
 * Методы клиента оборачивают gRPC вызовы и преобразуют ошибки в исключения
 * сервиса Orderservice.
 */
@Service
@Slf4j
public class InventoryGrpcClient {

    /**
     * gRPC stub для синхронного взаимодействия с Inventory Service.
     */
    @GrpcClient("inventory-service")
    private InventoryServiceGrpc.InventoryServiceBlockingStub blockingStub;

    /**
     * Получает информацию о продукте по его имени.
     *
     * @param name    имя продукта для поиска
     * @param traceId уникальный идентификатор запроса для логирования и трассировки
     * @return {@link ProductGrpcResponse} с информацией о продукте
     * @throws ProductNotFoundException если продукт не найден на складе
     */
    public ProductGrpcResponse getProductByName(String name, String traceId) {
        try {
            ProductGrpcRequest productRequest = ProductGrpcRequest.newBuilder()
                    .setName(name)
                    .setTraceId(traceId)
                    .build();

            return blockingStub.getProductByName(productRequest);
        } catch (StatusRuntimeException ex) {
            log.warn("trace_id = {}, Товара не существует", traceId);
            throw new ProductNotFoundException(ProductNotFoundException.PRODUCT_NOT_FOUND);
        }
    }

    /**
     * Получает информацию о списке продуктов по именам.
     *
     * @param listName список имен продуктов
     * @param traceId  уникальный идентификатор запроса для логирования и трассировки
     * @return {@link ProductBatchGrpcResponse} с информацией о найденных продуктах
     * @throws ProductNotFoundException если хотя бы один продукт из списка не найден
     */
    public ProductBatchGrpcResponse getListProductByName(List<String> listName, String traceId) {
        try {
            ProductBatchGrpcRequest request = ProductBatchGrpcRequest.newBuilder()
                    .addAllProductName(listName)
                    .setTraceId(traceId)
                    .build();

            return blockingStub.getListProductByName(request);
        } catch (StatusRuntimeException ex) {
            throw new ProductNotFoundException(ex.getStatus().getDescription());
        }
    }

    /**
     * Обновляет количество продуктов на складе после создания заказа.
     *
     * @param inventoryOutboxes список событий инвентаря с информацией о количестве продуктов
     * @param traceId           уникальный идентификатор запроса для логирования и трассировки
     */
    public void updateProductQuantities(List<InventoryOutbox> inventoryOutboxes, String traceId) {
        try {
            if (inventoryOutboxes.isEmpty()) {
                return;
            }

            List<ProductQuantityRequest> quantityRequests = inventoryOutboxes.stream()
                    .map(product -> ProductQuantityRequest.newBuilder()
                            .setProductName(product.getProductName())
                            .setQuantity(product.getQuantity())
                            .build())
                    .toList();

            ProductQuantityBatch quantityBatch = ProductQuantityBatch.newBuilder()
                    .addAllQuantityProducts(quantityRequests)
                    .setTraceId(traceId)
                    .build();

            log.info("trace_id = {}, отправка запроса на обновление количества продуктов", traceId);
            blockingStub.updateProductQuantities(quantityBatch);
            log.info("trace_id = {}, количество продуктов успешно обновлено.", traceId);
        } catch (Exception ex) {
            log.warn("trace_id = {}, ошибка обновления количества продуктов.", traceId, ex);
        }
    }
}