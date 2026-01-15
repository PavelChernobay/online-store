package org.onlinestore.orderservice.service.impl;

import lombok.RequiredArgsConstructor;
import org.onlinestore.orderservice.dto.OrderProductResponse;
import org.onlinestore.orderservice.entity.OrderProduct;
import org.onlinestore.orderservice.mapper.OrderProductMapper;
import org.onlinestore.orderservice.repository.OrderProductRepository;
import org.onlinestore.orderservice.service.OrderProductService;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class OrderProductServiceImpl implements OrderProductService {

    private final OrderProductRepository orderProductRepository;
    private final OrderProductMapper orderProductMapper;

    @Override
    public OrderProductResponse saveOrderProduct(OrderProduct orderProduct) {
        return orderProductMapper.orderProductToOrderProductResponse(
                orderProductRepository.save(orderProduct));
    }
}
