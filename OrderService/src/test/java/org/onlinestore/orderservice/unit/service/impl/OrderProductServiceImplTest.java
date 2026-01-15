package org.onlinestore.orderservice.unit.service.impl;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.onlinestore.orderservice.dto.OrderProductResponse;
import org.onlinestore.orderservice.entity.OrderProduct;
import org.onlinestore.orderservice.generator.TestDataGenerator;
import org.onlinestore.orderservice.mapper.OrderProductMapper;
import org.onlinestore.orderservice.repository.OrderProductRepository;
import org.onlinestore.orderservice.service.impl.OrderProductServiceImpl;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class OrderProductServiceImplTest {

    private OrderProduct orderProduct;
    private OrderProductResponse orderProductResponse;

    @Mock
    private OrderProductRepository orderProductRepository;

    @Mock
    private OrderProductMapper orderProductMapper;

    @InjectMocks
    private OrderProductServiceImpl orderProductService;

    @BeforeEach
    void setUp() {
        orderProduct = TestDataGenerator.generateOrderProduct();
        orderProductResponse = TestDataGenerator.generateOrderProductResponse();
    }

    @Test
    void saveOrderProductSuccess() {
        when(orderProductRepository.save(any(OrderProduct.class))).thenReturn(orderProduct);
        when(orderProductMapper.orderProductToOrderProductResponse(any(OrderProduct.class)))
                .thenReturn(orderProductResponse);

        OrderProductResponse result = orderProductService.saveOrderProduct(orderProduct);

        assertThat(result).isEqualTo(orderProductResponse);

        verify(orderProductRepository, times(1)).save(any(OrderProduct.class));
        verify(orderProductMapper, times(1))
                .orderProductToOrderProductResponse(any(OrderProduct.class));
    }
}