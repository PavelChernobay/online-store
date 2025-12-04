package org.onlinetstore.inventoryservice.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.onlinetstore.inventoryservice.dto.CreateProduct;
import org.onlinetstore.inventoryservice.dto.ProductResponse;
import org.onlinetstore.inventoryservice.entity.Product;

import java.util.List;

/**
 * Mapper для преобразования сущностей {@link Product} в DTO {@link ProductResponse} и обратно.
 * <p>
 * Используется MapStruct для автоматической генерации реализации маппинга.
 */
@Mapper(componentModel = "spring")
public interface ProductMapper {

    /**
     * Преобразует DTO {@link CreateProduct} в сущность {@link Product}.
     * Поле {@code id} игнорируется, так как оно генерируется базой данных.
     *
     * @param createProduct DTO с данными для создания продукта
     * @return сущность {@link Product} для сохранения в базу
     */
    @Mapping(target = "id", ignore = true)
    Product createProductToProduct(CreateProduct createProduct);

    /**
     * Преобразует сущность {@link Product} в DTO {@link ProductResponse}.
     *
     * @param product сущность продукта
     * @return DTO с данными продукта для ответа клиенту
     */
    ProductResponse productToProductResponse(Product product);

    /**
     * Преобразует список сущностей {@link Product} в список DTO {@link ProductResponse}.
     *
     * @param products список сущностей продуктов
     * @return список DTO продуктов
     */
    List<ProductResponse> productsToProductResponses(List<Product> products);

}
