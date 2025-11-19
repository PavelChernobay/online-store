package org.onlinestore.orderservice.integration.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.onlinestore.orderservice.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.security.test.context.support.WithUserDetails;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.patch;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
@Transactional
@ActiveProfiles("test")
@Sql("/test-data.sql")
@WithMockUser(roles = "ADMIN")
class UserControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void getAllUsers() throws Exception {
        mockMvc.perform(get("/api/users")
                .param("page", "0")
                .param("size", "10")
                .param("sortBy", "username")
                .param("ascending", "true"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content.length()").value(2))
                .andExpect(jsonPath("$.content[0].username").value("elona"))
                .andExpect(jsonPath("$.content[1].username").value("pavel"));
    }

    @Test
    void deleteUser() throws Exception {
        UUID uuid = UUID.fromString("22222222-2222-2222-2222-222222222222");
        mockMvc.perform(delete("/api/users/" + uuid))
                .andExpect(status().isNoContent());

        assertFalse(userRepository.findById(uuid).isPresent());
    }

    @Test
    void updateUserRole() throws Exception {
        UUID uuid = UUID.fromString("11111111-1111-1111-1111-111111111111");
        mockMvc.perform(patch("/api/users/" + uuid)
                .param("role","ROLE_ADMIN"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.role").value("ROLE_ADMIN"));
    }

    @Test
    @WithUserDetails(value = "pavel", userDetailsServiceBeanName = "customUserDetailsService")
    void getInfoUser() throws Exception {
        mockMvc.perform(get("/api/users/me"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.username").value("pavel"));
    }

}