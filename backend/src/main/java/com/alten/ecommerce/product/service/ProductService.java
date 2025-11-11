package com.alten.ecommerce.product.service;

import com.alten.ecommerce.product.back.repo.ProductRepository;
import com.alten.ecommerce.product.dto.ProductDTO;
import com.alten.ecommerce.product.exception.ProductNotFoundException;
import com.alten.ecommerce.product.mapper.ProductMapper;
import lombok.RequiredArgsConstructor;
import org.mapstruct.factory.Mappers;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class ProductService {

    private static final Logger log = LoggerFactory.getLogger(ProductService.class);

    @Autowired
    private ProductRepository repo;
    private final ProductMapper mapper = Mappers.getMapper(ProductMapper.class);

    public List<ProductDTO> findAll() {
        log.debug("Fetching all products");
        return repo.findAll()
                .stream()
                .map(mapper::toDTO)
                .toList();
    }

    public ProductDTO findById(Long id) {
        log.debug("Fetching product with id={}", id);
        return repo.findById(id)
                .map(mapper::toDTO)
                .orElseThrow(() -> {
                    log.warn("Product with id={} not found", id);
                    return new ProductNotFoundException(id);
                });
    }

    public ProductDTO create(ProductDTO dto) {
        log.info("Creating product with code={}", dto.getCode());
        var entity = mapper.toEntity(dto);
        var saved = repo.save(entity);
        log.debug("Product created with id={}", saved.getId());
        return mapper.toDTO(saved);
    }

    public ProductDTO update(Long id, ProductDTO dto) {
        log.info("Updating product id={}", id);

        var existing = repo.findById(id)
                .orElseThrow(() -> {
                    log.warn("Cannot update, product id={} not found", id);
                    return new ProductNotFoundException(id);
                });

        // On remappe depuis le DTO
        var toUpdate = mapper.toEntity(dto);
        // On garde les infos qu'on ne veut pas perdre
        toUpdate.setId(existing.getId());
        toUpdate.setCreatedAt(existing.getCreatedAt());

        var saved = repo.save(toUpdate);
        log.debug("Product id={} updated", id);
        return mapper.toDTO(saved);
    }

    public void delete(Long id) {
        log.info("Deleting product id={}", id);
        if (!repo.existsById(id)) {
            log.warn("Cannot delete, product id={} not found", id);
            throw new ProductNotFoundException(id);
        }
        repo.deleteById(id);
        log.debug("Product id={} deleted", id);
    }
}
