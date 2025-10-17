package org.onlinestore.orderservice.grpc.client;

import io.grpc.StatusRuntimeException;
import net.devh.boot.grpc.client.inject.GrpcClient;
import org.onlinestore.orderservice.exception.ProductNotFoundException;
import org.onlinestore.orderservice.grpc.InventoryServiceGrpc;
import org.onlinestore.orderservice.grpc.ProductBatchGrpcRequest;
import org.onlinestore.orderservice.grpc.ProductBatchGrpcResponse;
import org.onlinestore.orderservice.grpc.ProductGrpcRequest;
import org.onlinestore.orderservice.grpc.ProductGrpcResponse;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
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
}
