package org.onlinetstore.inventoryservice.grpc.server;

import com.google.protobuf.Empty;
import io.grpc.Status;
import io.grpc.stub.StreamObserver;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.onlinestore.common.grpc.InventoryServiceGrpc;
import org.onlinestore.common.grpc.ProductBatchGrpcRequest;
import org.onlinestore.common.grpc.ProductBatchGrpcResponse;
import org.onlinestore.common.grpc.ProductGrpcRequest;
import org.onlinestore.common.grpc.ProductGrpcResponse;
import org.onlinestore.common.grpc.ProductQuantityBatch;
import org.onlinetstore.inventoryservice.entity.Product;
import org.onlinetstore.inventoryservice.service.ProductService;
import org.onlinetstore.inventoryservice.validate.ProductValidate;
import org.springframework.grpc.server.service.GrpcService;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * gRPC сервис для работы со складом (InventoryService).
 * <p>
 * Предоставляет методы для получения информации о продуктах по имени,
 * получения списка продуктов и обновления количества продуктов на складе.
 * Использует валидацию продуктов через {@link ProductValidate} и бизнес-логику через {@link ProductService}.
 */
@GrpcService
@RequiredArgsConstructor
@Slf4j
public class InventoryGrpcService extends InventoryServiceGrpc.InventoryServiceImplBase {

    private final ProductValidate productValidate;
    private final ProductService productService;

    /**
     * Получает продукт по имени.
     *
     * @param request          gRPC запрос с именем продукта и traceId
     * @param responseObserver объект для отправки gRPC ответа или ошибки
     */
    @Override
    public void getProductByName(ProductGrpcRequest request, StreamObserver<ProductGrpcResponse> responseObserver) {
        log.info("trace_id = {}, получен запрос на добавления товара.", request.getTraceId());

        Product product = productValidate.checkProductByName(request.getName());

        if (product == null) {
            log.warn("trace_id = {}, продукт не найден", request.getTraceId());
            responseObserver.onError(Status.NOT_FOUND.asException());
            return;
        }

        ProductGrpcResponse productGrpcResponse = getProductGrpcResponse(product, request.getTraceId());

        log.info("trace_id = {}, отправляем ответ со склада.", request.getTraceId());

        responseObserver.onNext(productGrpcResponse);
        responseObserver.onCompleted();
    }

    /**
     * Преобразует сущность {@link Product} в gRPC ответ {@link ProductGrpcResponse}.
     *
     * @param product сущность продукта
     * @param traceId идентификатор запроса для трассировки
     * @return gRPC ответ с данными продукта
     */
    private ProductGrpcResponse getProductGrpcResponse(Product product, String traceId) {
        return ProductGrpcResponse.newBuilder()
                .setId(product.getId().toString())
                .setName(product.getName())
                .setQuantity(product.getQuantity())
                .setPrice(product.getPrice().doubleValue())
                .setSale(product.getSale())
                .setTraceId(traceId)
                .build();
    }

    /**
     * Получает список продуктов по именам.
     *
     * @param request          gRPC запрос с именами продуктов и traceId
     * @param responseObserver объект для отправки gRPC ответа или ошибки
     */
    @Override
    public void getListProductByName(ProductBatchGrpcRequest request, StreamObserver<ProductBatchGrpcResponse> responseObserver) {
        log.info("trace_id = {}, получен запрос на проверку списка продуктов.", request.getTraceId());

        List<String> notFoundProducts = new ArrayList<>();

        List<ProductGrpcResponse> grpcResponseList = request.getProductNameList().stream()
                .map(name -> {
                    Product product = productValidate.checkProductByName(name);

                    if (product == null) {
                        notFoundProducts.add(name);
                        return null;
                    }

                    return getProductGrpcResponse(product, request.getTraceId());
                })
                .filter(Objects::nonNull)
                .toList();

        if (!notFoundProducts.isEmpty()) {
            log.warn("trace_id = {}, продукты не найдены: {}", request.getTraceId(), notFoundProducts);
            responseObserver.onError(
                    Status.NOT_FOUND
                            .withDescription(String.format("Продуктов %s нет в продаже.",
                                    String.join(" ,", notFoundProducts)))
                            .asException()
            );
            return;
        }

        ProductBatchGrpcResponse grpcResponse = ProductBatchGrpcResponse.newBuilder()
                .addAllProductResponse(grpcResponseList)
                .setTraceId(request.getTraceId())
                .build();

        log.info("trace_id = {}, ответ со склада.", request.getTraceId());

        responseObserver.onNext(grpcResponse);
        responseObserver.onCompleted();
    }

    /**
     * Обновляет количество продуктов на складе.
     *
     * @param request          gRPC запрос с количеством продуктов и traceId
     * @param responseObserver объект для отправки gRPC ответа (Empty) после успешного обновления
     */
    @Override
    public void updateProductQuantities(ProductQuantityBatch request, StreamObserver<Empty> responseObserver) {
        log.info("trace_id = {}, запрос на обновления количества продуктов", request.getTraceId());
        request.getQuantityProductsList().forEach(product ->{
            productService.updateProductQuantities(product.getProductName(), product.getQuantity());
        });

        log.info("trace_id = {}, количество продуктов успешно обновлено.", request.getTraceId());

        responseObserver.onNext(Empty.getDefaultInstance());
        responseObserver.onCompleted();
    }
}
