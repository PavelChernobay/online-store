package org.onlinestore.orderservice.repository;

import org.onlinestore.orderservice.entity.Basket;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface BasketRepository extends JpaRepository<Basket, UUID> {

    Optional<Basket> findByUserId(UUID id);

    @EntityGraph(attributePaths = "productItems")
    Optional<Basket> findWithProductItemsByUserId(UUID id);

}
