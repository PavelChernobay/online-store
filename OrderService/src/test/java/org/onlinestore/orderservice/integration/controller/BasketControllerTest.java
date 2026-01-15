package org.onlinestore.orderservice.integration.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.onlinestore.common.grpc.ProductGrpcResponse;
import org.onlinestore.orderservice.dto.CreateProduct;
import org.onlinestore.orderservice.grpc.client.InventoryGrpcClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithUserDetails;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
@Sql("/test-data.sql")
@WithUserDetails(value = "pavel", userDetailsServiceBeanName = "customUserDetailsService")
@Transactional
class BasketControllerTest {

    private ProductGrpcResponse productGrpcResponse;

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockitoBean
    private InventoryGrpcClient inventoryGrpcClient;

    @BeforeEach
    void setUp() {
        productGrpcResponse = ProductGrpcResponse.newBuilder()
                .setId("aaaaaaa1-aaaa-aaaa-aaaa-aaaaaaaaaaa3")
                .setName("Product3")
                .setQuantity(1)
                .setPrice(300)
                .setSale(0)
                .build();
    }

    @Test
    void addProductToBasket() throws Exception {
        CreateProduct createProduct = CreateProduct.builder()
                .productName("Product3")
                .quantity(1)
                .build();

        when(inventoryGrpcClient.getProductByName(any(), anyString())).thenReturn(productGrpcResponse);

        mockMvc.perform(post("/api/baskets")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(createProduct)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.userId").value("11111111-1111-1111-1111-111111111111"))
                .andExpect(jsonPath("$.products[2].quantity").value(1))
                .andExpect(jsonPath("$.products[2].name").value("Product3"))
                .andExpect(jsonPath("$.totalSum").value(600));
    }

    @Test
    void getBasketCurrentUser() throws Exception {
        mockMvc.perform(get("/api/baskets"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.products.length()").value(2))
                .andExpect(jsonPath("$.products[0].name").value("Product1"))
                .andExpect(jsonPath("$.products[0].quantity").value(1))
                .andExpect(jsonPath("$.products[1].name").value("Product2"))
                .andExpect(jsonPath("$.products[1].quantity").value(1));
    }

    @Test
    void deleteProductInBasket() throws Exception {
        mockMvc.perform(delete("/api/baskets")
                .param( "id","cccccccc-cccc-cccc-cccc-cccccccccccc"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.products.length()").value(1))
                .andExpect(jsonPath("$.products[0].name").value("Product2"))
                .andExpect(jsonPath("$.products[0].quantity").value(1));
    }

    @Test
    void getAllProductsInBasket() throws Exception {
        mockMvc.perform(get("/api/baskets/products"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(2))
                .andExpect(jsonPath("$[0].name").value("Product1"))
                .andExpect(jsonPath("$[0].quantity").value(1))
                .andExpect(jsonPath("$[1].name").value("Product2"))
                .andExpect(jsonPath("$[1].quantity").value(1));
    }

    @Test
    void clearProductInBasket() throws Exception {
        mockMvc.perform(delete("/api/baskets/products"))
                .andExpect(status().isNoContent());
    }
}