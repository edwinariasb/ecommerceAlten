package com.alten.ecommerce.product.mapper;

import com.alten.ecommerce.product.back.entity.Product;
import com.alten.ecommerce.product.dto.ProductDTO;
import com.alten.ecommerce.product.enums.InventoryStatus;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;

import static org.junit.jupiter.api.Assertions.*;

class ProductMapperTest {

    private final ProductMapper mapper = Mappers.getMapper(ProductMapper.class);

    @Test
    void toDTO_should_map_all_fields() {
        Product p = new Product();
        p.setId(1L);
        p.setCode("P-001");
        p.setName("Laptop");
        p.setDescription("Desc");
        p.setImage("img.png");
        p.setCategory("Informatique");
        p.setPrice(999.0);
        p.setQuantity(5);
        p.setInternalReference("INT-1");
        p.setShellId(10L);
        p.setInventoryStatus(InventoryStatus.INSTOCK);
        p.setRating(4);
        p.setCreatedAt(100L);
        p.setUpdatedAt(200L);

        ProductDTO dto = mapper.toDTO(p);

        assertNotNull(dto);
        assertEquals(p.getId(), dto.getId());
        assertEquals(p.getCode(), dto.getCode());
        assertEquals(p.getName(), dto.getName());
        assertEquals(p.getDescription(), dto.getDescription());
        assertEquals(p.getImage(), dto.getImage());
        assertEquals(p.getCategory(), dto.getCategory());
        assertEquals(p.getPrice(), dto.getPrice());
        assertEquals(p.getQuantity(), dto.getQuantity());
        assertEquals(p.getInternalReference(), dto.getInternalReference());
        assertEquals(p.getShellId(), dto.getShellId());
        assertEquals(p.getInventoryStatus(), dto.getInventoryStatus());
        assertEquals(p.getRating(), dto.getRating());
        assertEquals(p.getCreatedAt(), dto.getCreatedAt());
        assertEquals(p.getUpdatedAt(), dto.getUpdatedAt());
    }

    @Test
    void toEntity_should_map_basic_fields() {
        ProductDTO dto = new ProductDTO(
                1L,
                "P-002",
                "Phone",
                "Smartphone",
                "img2.png",
                "Mobiles",
                500.0,
                3,
                "INT-2",
                20L,
                InventoryStatus.LOWSTOCK,
                5,
                123L,
                456L
        );

        Product entity = mapper.toEntity(dto);

        assertNotNull(entity);
        assertEquals(dto.getCode(), entity.getCode());
        assertEquals(dto.getName(), entity.getName());
        assertEquals(dto.getDescription(), entity.getDescription());
        assertEquals(dto.getImage(), entity.getImage());
        assertEquals(dto.getCategory(), entity.getCategory());
        assertEquals(dto.getPrice(), entity.getPrice());
        assertEquals(dto.getQuantity(), entity.getQuantity());
        assertEquals(dto.getInternalReference(), entity.getInternalReference());
        assertEquals(dto.getShellId(), entity.getShellId());
        assertEquals(dto.getInventoryStatus(), entity.getInventoryStatus());
        assertEquals(dto.getRating(), entity.getRating());
    }
}
