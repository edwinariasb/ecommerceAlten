package com.alten.ecommerce.product.dto;

// /token
public record LoginRequest(
        String email,
        String password
) {}
