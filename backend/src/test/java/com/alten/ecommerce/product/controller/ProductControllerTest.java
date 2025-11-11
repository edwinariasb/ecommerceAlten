package com.alten.ecommerce.product.controller;

import com.alten.ecommerce.product.dto.ProductDTO;
import com.alten.ecommerce.product.enums.InventoryStatus;
import com.alten.ecommerce.product.service.ProductService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.mockito.ArgumentMatchers.anyLong;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(ProductController.class)
class ProductControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ProductService service;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void getAll_should_return_list() throws Exception {
        ProductDTO dto = new ProductDTO(
                1L, "P-1", "Prod 1", "Desc", null,
                "Cat", 10.0, 1, "INT", 1L,
                InventoryStatus.INSTOCK, 4, 100L, 200L
        );
        Mockito.when(service.findAll()).thenReturn(List.of(dto));

        mockMvc.perform(get("/api/products"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value(1L))
                .andExpect(jsonPath("$[0].name").value("Prod 1"));
    }

    @Test
    void getOne_should_return_product() throws Exception {
        ProductDTO dto = new ProductDTO(
                1L, "P-1", "Prod 1", "Desc", null,
                "Cat", 10.0, 1, "INT", 1L,
                InventoryStatus.INSTOCK, 4, 100L, 200L
        );
        Mockito.when(service.findById(anyLong())).thenReturn(dto);

        mockMvc.perform(get("/api/products/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1L));
    }

    @Test
    void create_should_return_created() throws Exception {
        ProductDTO input = new ProductDTO(
                null, "P-1", "Prod 1", "Desc", null,
                "Cat", 10.0, 1, "INT", 1L,
                InventoryStatus.INSTOCK, 4, null, null
        );
        ProductDTO output = new ProductDTO(
                1L, "P-1", "Prod 1", "Desc", null,
                "Cat", 10.0, 1, "INT", 1L,
                InventoryStatus.INSTOCK, 4, 100L, 200L
        );
        Mockito.when(service.create(Mockito.any())).thenReturn(output);

        mockMvc.perform(post("/api/products")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(input)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value(1L));
    }

    @Test
    void delete_should_return_no_content() throws Exception {
        mockMvc.perform(delete("/api/products/1"))
                .andExpect(status().isNoContent());
        Mockito.verify(service).delete(1L);
    }
}
