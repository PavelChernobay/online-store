package org.onlinestore.orderservice.repository;

import org.onlinestore.orderservice.entity.BasketProduct;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface BasketProductRepository extends JpaRepository<BasketProduct, UUID> {

    Optional<BasketProduct> findByName(String productName);

}
