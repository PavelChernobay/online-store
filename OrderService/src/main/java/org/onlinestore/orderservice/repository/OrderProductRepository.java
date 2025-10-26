package org.onlinestore.orderservice.repository;

import org.onlinestore.orderservice.entity.OrderProduct;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@RestController
public interface OrderProductRepository extends JpaRepository<OrderProduct, UUID> {
}
