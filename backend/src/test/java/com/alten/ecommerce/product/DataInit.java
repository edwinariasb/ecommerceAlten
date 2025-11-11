package com.alten.ecommerce.product;

import com.alten.ecommerce.product.enums.InventoryStatus;
import com.alten.ecommerce.product.back.entity.Product;
import com.alten.ecommerce.product.back.repo.ProductRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class DataInit {

    @Bean
    CommandLineRunner initProducts(ProductRepository repo) {
        return args -> {
            if (repo.count() == 0) {
                Product p = new Product();
                p.setCode("P-001");
                p.setName("Ordinateur portable");
                p.setDescription("Un PC léger pour le travail.");
                p.setCategory("Informatique");
                p.setPrice(999.99);
                p.setQuantity(10);
                p.setInternalReference("INT-001");
                p.setShellId(1L);
                p.setInventoryStatus(InventoryStatus.INSTOCK);
                p.setRating(4);
                repo.save(p);
            }
        };
    }
}

