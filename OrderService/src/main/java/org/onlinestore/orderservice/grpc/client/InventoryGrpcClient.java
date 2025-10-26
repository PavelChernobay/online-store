package org.onlinestore.orderservice.grpc.client;

import io.grpc.StatusRuntimeException;
import lombok.extern.slf4j.Slf4j;
import net.devh.boot.grpc.client.inject.GrpcClient;
import org.onlinestore.orderservice.entity.InventoryOutbox;
import org.onlinestore.orderservice.exception.ProductNotFoundException;
import org.onlinestore.orderservice.grpc.InventoryServiceGrpc;
import org.onlinestore.orderservice.grpc.ProductBatchGrpcRequest;
import org.onlinestore.orderservice.grpc.ProductBatchGrpcResponse;
import org.onlinestore.orderservice.grpc.ProductGrpcRequest;
import org.onlinestore.orderservice.grpc.ProductGrpcResponse;
import org.onlinestore.orderservice.grpc.ProductQuantityBatch;
import org.onlinestore.orderservice.grpc.ProductQuantityRequest;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
public class InventoryGrpcClient {

    @GrpcClient("inventory-service")
    private InventoryServiceGrpc.InventoryServiceBlockingStub blockingStub;

    public ProductGrpcResponse getProductByName(String name) {
        try {
            ProductGrpcRequest productRequest = ProductGrpcRequest.newBuilder()
                    .setName(name)
                    .build();

            return blockingStub.getProductByName(productRequest);
        } catch (StatusRuntimeException ex) {
            throw new ProductNotFoundException(ProductNotFoundException.PRODUCT_NOT_FOUND);
        }
    }

    public ProductBatchGrpcResponse getListProductByName(List<String> listName) {
        try {
            ProductBatchGrpcRequest request = ProductBatchGrpcRequest.newBuilder()
                    .addAllProductName(listName)
                    .build();

            return blockingStub.getListProductByName(request);
        } catch (StatusRuntimeException ex) {
            throw new ProductNotFoundException(ex.getStatus().getDescription());
        }
    }

    public void updateProductQuantities(List<InventoryOutbox> inventoryOutboxes) {
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
                    .build();
            blockingStub.updateProductQuantities(quantityBatch);
        } catch (Exception ex) {
            log.warn("Ошибка обновления количества продуктов", ex);
        }
    }
}
