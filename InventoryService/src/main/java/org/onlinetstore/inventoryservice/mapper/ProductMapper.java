package org.onlinetstore.inventoryservice.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.onlinetstore.inventoryservice.dto.CreateProduct;
import org.onlinetstore.inventoryservice.dto.ProductResponse;
import org.onlinetstore.inventoryservice.entity.Product;

import java.util.List;

@Mapper(componentModel = "spring")
public interface ProductMapper {

    @Mapping(target = "id", ignore = true)
    Product createProductToProduct(CreateProduct createProduct);

    ProductResponse productToProductResponse(Product product);

    List<ProductResponse> productsToProductResponses(List<Product> products);

}
