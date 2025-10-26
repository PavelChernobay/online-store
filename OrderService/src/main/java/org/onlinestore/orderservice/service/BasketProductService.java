package org.onlinestore.orderservice.service;

import org.onlinestore.orderservice.dto.CreateProduct;
import org.onlinestore.orderservice.entity.BasketProduct;

public interface BasketProductService {

    BasketProduct createProductItem(CreateProduct createProduct);

}
