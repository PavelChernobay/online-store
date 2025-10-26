package org.onlinetstore.inventoryservice.grpc.server;

import com.google.protobuf.Empty;
import io.grpc.Status;
import io.grpc.stub.StreamObserver;
import lombok.RequiredArgsConstructor;
import org.onlinestore.orderservice.grpc.InventoryServiceGrpc;
import org.onlinestore.orderservice.grpc.ProductBatchGrpcRequest;
import org.onlinestore.orderservice.grpc.ProductBatchGrpcResponse;
import org.onlinestore.orderservice.grpc.ProductGrpcRequest;
import org.onlinestore.orderservice.grpc.ProductGrpcResponse;
import org.onlinestore.orderservice.grpc.ProductQuantityBatch;
import org.onlinetstore.inventoryservice.entity.Product;
import org.onlinetstore.inventoryservice.service.ProductService;
import org.onlinetstore.inventoryservice.validate.ProductValidate;
import org.springframework.grpc.server.service.GrpcService;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@GrpcService
@RequiredArgsConstructor
public class InventoryGrpcService extends InventoryServiceGrpc.InventoryServiceImplBase {

    private final ProductValidate productValidate;
    private final ProductService productService;

    @Override
    public void getProductByName(ProductGrpcRequest request, StreamObserver<ProductGrpcResponse> responseObserver) {
        Product product = productValidate.checkProductByName(request.getName());

        if (product == null) {
            responseObserver.onError(Status.NOT_FOUND.asException());
            return;
        }

        ProductGrpcResponse productGrpcResponse = getProductGrpcResponse(product);

        responseObserver.onNext(productGrpcResponse);
        responseObserver.onCompleted();
    }

    private ProductGrpcResponse getProductGrpcResponse(Product product) {
        return ProductGrpcResponse.newBuilder()
                .setId(product.getId().toString())
                .setName(product.getName())
                .setQuantity(product.getQuantity())
                .setPrice(product.getPrice().doubleValue())
                .setSale(product.getSale())
                .build();
    }

    @Override
    public void getListProductByName(ProductBatchGrpcRequest request, StreamObserver<ProductBatchGrpcResponse> responseObserver) {
        List<String> notFoundProducts = new ArrayList<>();

        List<ProductGrpcResponse> grpcResponseList = request.getProductNameList().stream()
                .map(name -> {
                    Product product = productValidate.checkProductByName(name);

                    if (product == null) {
                        notFoundProducts.add(name);
                        return null;
                    }

                    return getProductGrpcResponse(product);
                })
                .filter(Objects::nonNull)
                .toList();

        if (!notFoundProducts.isEmpty()) {
            responseObserver.onError(
                    Status.NOT_FOUND
                            .withDescription(String.format("Продуктов %s нет в продаже",
                                    String.join(" ,", notFoundProducts)))
                            .asException()
            );
            return;
        }

        ProductBatchGrpcResponse grpcResponse = ProductBatchGrpcResponse.newBuilder()
                .addAllProductResponse(grpcResponseList)
                .build();

        responseObserver.onNext(grpcResponse);
        responseObserver.onCompleted();
    }

    @Override
    public void updateProductQuantities(ProductQuantityBatch request, StreamObserver<Empty> responseObserver) {
        request.getQuantityProductsList().forEach(product ->{
            productService.updateProductQuantities(product.getProductName(), product.getQuantity());
        });

        responseObserver.onNext(Empty.getDefaultInstance());
        responseObserver.onCompleted();
    }
}
