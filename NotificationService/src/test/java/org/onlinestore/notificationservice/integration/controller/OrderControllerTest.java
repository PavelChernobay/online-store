package org.onlinestore.notificationservice.integration.controller;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
@Sql("/test-data.sql")
@Transactional
class OrderControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    void getAllOrders() throws Exception {
        mockMvc.perform(get("/api/orders/all")
                .param("page", "0")
                .param("size", "10")
                .param("sortBy", "createdAt")
                .param("ascending", "true"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content.length()").value(2));

    }

    @Test
    void getAllOrdersByOrderId() throws Exception {
        UUID uuid = UUID.fromString("aaaaaaa1-aaaa-aaaa-aaaa-aaaaaaaa0001");

        mockMvc.perform(get("/api/orders/" + uuid)
                        .param("page", "0")
                        .param("size", "10")
                        .param("sortBy", "createdAt")
                        .param("ascending", "true"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content.length()").value(1));
    }

    @Test
    void getAllOrdersByUserId() throws Exception {
        UUID uuid = UUID.fromString("ccccccc1-cccc-cccc-cccc-cccccccc0001");

        mockMvc.perform(get("/api/orders/users/" + uuid)
                        .param("page", "0")
                        .param("size", "10")
                        .param("sortBy", "createdAt")
                        .param("ascending", "true"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content.length()").value(1));
    }
}