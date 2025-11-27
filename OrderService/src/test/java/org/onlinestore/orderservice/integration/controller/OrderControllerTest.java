package org.onlinestore.orderservice.integration.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.onlinestore.orderservice.entity.Status;
import org.onlinestore.orderservice.grpc.ProductBatchGrpcResponse;
import org.onlinestore.orderservice.grpc.ProductGrpcResponse;
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

import static org.mockito.ArgumentMatchers.anyList;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
@Sql("/test-data.sql")
@WithUserDetails(value = "pavel", userDetailsServiceBeanName = "customUserDetailsService")
@Transactional
class OrderControllerTest {

    private ProductBatchGrpcResponse productBatchGrpcResponse;

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockitoBean
    private InventoryGrpcClient inventoryGrpcClient;

    @BeforeEach
    void setUp() {
        ProductGrpcResponse product1 = ProductGrpcResponse.newBuilder()
                .setId("aaaaaaa1-aaaa-aaaa-aaaa-aaaaaaaaaaa1")
                .setName("Product1")
                .setQuantity(1)
                .setPrice(100)
                .setSale(0)
                .build();
        ProductGrpcResponse product2 = ProductGrpcResponse.newBuilder()
                .setId("aaaaaaa2-aaaa-aaaa-aaaa-aaaaaaaaaaa2")
                .setName("Product2")
                .setQuantity(1)
                .setPrice(200)
                .setSale(0)
                .build();

        productBatchGrpcResponse = ProductBatchGrpcResponse.newBuilder()
                .addProductResponse(product1)
                .addProductResponse(product2)
                .build();
    }

    @Test
    void createOrder() throws Exception {
        when(inventoryGrpcClient.getListProductByName(anyList(), anyString())).thenReturn(productBatchGrpcResponse);

        mockMvc.perform(post("/api/orders")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.status").value(Status.CREATED.name()))
                .andExpect(jsonPath("$.totalSum").value(300))
                .andExpect(jsonPath("$.products.length()").value(2));
    }

}