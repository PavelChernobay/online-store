package org.onlinestore.orderservice.repository;

import org.onlinestore.orderservice.entity.ProductItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface ProductItemRepository extends JpaRepository<ProductItem, UUID> {

    Optional<ProductItem> findByName(String productName);

}
