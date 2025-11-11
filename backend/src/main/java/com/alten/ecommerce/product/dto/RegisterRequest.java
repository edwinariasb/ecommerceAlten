package com.alten.ecommerce.product.dto;

// /account
public record RegisterRequest(
        String username,
        String firstname,
        String email,
        String password
) {}
