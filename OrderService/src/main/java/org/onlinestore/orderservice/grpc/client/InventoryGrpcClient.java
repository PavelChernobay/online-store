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

@Service
@Slf4j
public class InventoryGrpcClient {

    @GrpcClient("inventory-service")
    private InventoryServiceGrpc.InventoryServiceBlockingStub blockingStub;

    public ProductGrpcResponse getProductByName(String name, String traceId) {
        try {
            ProductGrpcRequest productRequest = ProductGrpcRequest.newBuilder()
                    .setName(name)
                    .setTraceId(traceId)
                    .build();

            return blockingStub.getProductByName(productRequest);
        } catch (StatusRuntimeException ex) {
            throw new ProductNotFoundException(ProductNotFoundException.PRODUCT_NOT_FOUND);
        }
    }

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

            log.info("trace_id = {}, отправка запроса на обновления количества продуктов", traceId);
            blockingStub.updateProductQuantities(quantityBatch);
            log.info("trace_id = {}, количество продуктов успешно обновлено.", traceId);
        } catch (Exception ex) {
            log.warn("trace_id = {}, ошибка обновления количества продуктов.", traceId, ex);
        }
    }
}
