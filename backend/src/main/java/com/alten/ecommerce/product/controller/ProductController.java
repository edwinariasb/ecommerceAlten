package com.alten.ecommerce.product.controller;

import com.alten.ecommerce.product.dto.ProductDTO;
import com.alten.ecommerce.product.service.ProductService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/products")
@CrossOrigin(origins = "http://localhost:4200")
@RequiredArgsConstructor
public class ProductController {

    @Autowired
    private ProductService service;

    @GetMapping
    public List<ProductDTO> getAll() {
        return service.findAll();
    }

    @GetMapping("/{id}")
    public ProductDTO getOne(@PathVariable Long id) {
        return service.findById(id);
    }

    @PostMapping
    public ResponseEntity<ProductDTO> create(@RequestBody ProductDTO dto,
                                             HttpServletRequest request) {
        checkAdmin(request);
        return ResponseEntity.status(201).body(service.create(dto));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ProductDTO> update(@PathVariable Long id,
                                             @RequestBody ProductDTO dto,
                                             HttpServletRequest request) {
        checkAdmin(request);
        return ResponseEntity.ok(service.update(id, dto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id,
                                       HttpServletRequest request) {
        checkAdmin(request);
        service.delete(id);
        return ResponseEntity.noContent().build();
    }

    private void checkAdmin(HttpServletRequest request) {
        String email = (String) request.getAttribute("currentUserEmail");
        if (email == null || !email.equalsIgnoreCase("admin@admin.com")) {
            throw new RuntimeException("Forbidden");
        }
    }
}