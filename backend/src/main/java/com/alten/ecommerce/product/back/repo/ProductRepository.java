package com.alten.ecommerce.product.back.repo;

import com.alten.ecommerce.product.back.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ProductRepository extends JpaRepository<Product, Long> {
    Optional<Product> findByCode(String code);
}

