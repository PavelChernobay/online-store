package org.onlinetstore.inventoryservice.integration.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.onlinetstore.inventoryservice.dto.CreateProduct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.UUID;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
@Sql("/test-data.sql")
@Transactional
class ProductControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void createProduct() throws Exception {
        CreateProduct createProduct = CreateProduct.builder()
                .name("Product3")
                .quantity(1)
                .price(BigDecimal.valueOf(300))
                .build();

        mockMvc.perform(post("/api/products")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(createProduct)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.name").value("Product3"))
                .andExpect(jsonPath("$.quantity").value(1))
                .andExpect(jsonPath("$.price").value(300));
    }

    @Test
    void getProductById() throws Exception {
        UUID uuid = UUID.fromString("aaaaaaa1-aaaa-aaaa-aaaa-aaaaaaaaaaa1");

        mockMvc.perform(get("/api/products/" + uuid))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("Product1"))
                .andExpect(jsonPath("$.quantity").value(10))
                .andExpect(jsonPath("$.price").value(100));
    }

    @Test
    void getAllProducts() throws Exception {
        mockMvc.perform(get("/api/products")
                .param("page","0")
                .param("size","10")
                .param("sortBy","name")
                .param("ascending","true"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content.length()").value(2))
                .andExpect(jsonPath("$.content[0].name").value("Product1"))
                .andExpect(jsonPath("$.content[1].name").value("Product2"));
    }

    @Test
    void deleteProductById() throws Exception {
        UUID uuid = UUID.fromString("aaaaaaa1-aaaa-aaaa-aaaa-aaaaaaaaaaa1");

        mockMvc.perform(delete("/api/products/" + uuid))
                .andExpect(status().isNoContent());
    }
}