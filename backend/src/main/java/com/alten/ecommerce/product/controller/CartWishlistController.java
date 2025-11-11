package com.alten.ecommerce.product.controller;

import com.alten.ecommerce.product.service.UserCollectionsService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/cartWishlist")
@RequiredArgsConstructor
@CrossOrigin(origins = "http://localhost:4200")
public class CartWishlistController {

    @Autowired
    private UserCollectionsService userCollectionsService;

    @PostMapping("/cart/{productId}")
    public void addToCart(@PathVariable Long productId, HttpServletRequest request) {
        userCollectionsService.addToCart(productId, request);
    }

    @DeleteMapping("/cart/{productId}")
    public void removeFromCart(@PathVariable Long productId, HttpServletRequest request) {
        userCollectionsService.removeFromCart(productId, request);
    }

    @PostMapping("/wishlist/{productId}")
    public void addToWishlist(@PathVariable Long productId, HttpServletRequest request) {
        userCollectionsService.addToWishlist(productId, request);
    }

    @DeleteMapping("/wishlist/{productId}")
    public void removeFromWishlist(@PathVariable Long productId, HttpServletRequest request) {
        userCollectionsService.removeFromWishlist(productId, request);
    }
}
