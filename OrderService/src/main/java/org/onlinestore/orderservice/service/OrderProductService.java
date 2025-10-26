package org.onlinestore.orderservice.service;

import org.onlinestore.orderservice.dto.OrderProductResponse;
import org.onlinestore.orderservice.entity.OrderProduct;

public interface OrderProductService {

    OrderProductResponse saveOrderProduct(OrderProduct orderProduct);

}
