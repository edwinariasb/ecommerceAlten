package com.alten.ecommerce.product.service;

import com.alten.ecommerce.product.back.entity.Product;
import com.alten.ecommerce.product.back.repo.ProductRepository;
import com.alten.ecommerce.product.dto.ProductDTO;
import com.alten.ecommerce.product.enums.InventoryStatus;
import com.alten.ecommerce.product.mapper.ProductMapper;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mapstruct.factory.Mappers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ProductServiceTest {

    @Mock
    private ProductRepository repo;

    private final ProductMapper mapper = Mappers.getMapper(ProductMapper.class);

    @InjectMocks
    private ProductService service;

    @Test
    void findAll_should_return_dtos() {
        Product p = new Product();
        p.setId(1L);
        p.setName("Laptop");

        when(repo.findAll()).thenReturn(List.of(p));

        List<ProductDTO> result = service.findAll();

        assertEquals(1, result.size());
        assertEquals("Laptop", result.get(0).getName());
        verify(repo, times(1)).findAll();
    }

    @Test
    void findById_should_return_dto_when_exists() {
        Product p = new Product();
        p.setId(1L);
        p.setName("Phone");

        when(repo.findById(1L)).thenReturn(Optional.of(p));

        ProductDTO dto = service.findById(1L);

        assertNotNull(dto);
        assertEquals("Phone", dto.getName());
        verify(repo).findById(1L);
    }

    @Test
    void findById_should_throw_when_not_found() {
        when(repo.findById(99L)).thenReturn(Optional.empty());

        assertThrows(RuntimeException.class, () -> service.findById(99L));
    }

    @Test
    void create_should_save_and_return_dto() {
        ProductDTO dto = new ProductDTO(
                null, "P-001", "Tablet", "Desc", null,
                "Cat", 500.0, 5, "INT", 2L,
                InventoryStatus.INSTOCK, 4, null, null
        );

        Product saved = new Product();
        saved.setId(1L);
        saved.setName("Tablet");

        when(repo.save(any(Product.class))).thenReturn(saved);

        ProductDTO result = service.create(dto);

        assertEquals(1L, result.getId());
        verify(repo).save(any(Product.class));
    }

    @Test
    void delete_should_call_repo_when_exists() {
        when(repo.existsById(1L)).thenReturn(true);

        service.delete(1L);

        verify(repo).deleteById(1L);
    }

    @Test
    void delete_should_throw_when_not_exists() {
        when(repo.existsById(1L)).thenReturn(false);

        assertThrows(RuntimeException.class, () -> service.delete(1L));
        verify(repo, never()).deleteById(any());
    }
}