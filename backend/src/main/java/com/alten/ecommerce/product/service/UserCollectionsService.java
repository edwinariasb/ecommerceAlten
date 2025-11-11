package com.alten.ecommerce.product.service;

import com.alten.ecommerce.product.back.entity.AppUser;
import com.alten.ecommerce.product.back.entity.Product;
import com.alten.ecommerce.product.back.repo.AppUserRepository;
import com.alten.ecommerce.product.back.repo.ProductRepository;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
public class UserCollectionsService {

    @Autowired
    private CurrentUserService currentUserService;
    @Autowired
    private ProductRepository productRepo;
    @Autowired
    private AppUserRepository userRepo;

    public void addToCart(Long productId, HttpServletRequest request) {
        AppUser user = currentUserService.getCurrentUser(request);
        Product product = productRepo.findById(productId)
                .orElseThrow(() -> new RuntimeException("Product not found"));
        user.getCart().add(product);
        userRepo.save(user);
    }

    public void removeFromCart(Long productId, HttpServletRequest request) {
        AppUser user = currentUserService.getCurrentUser(request);
        user.getCart().removeIf(p -> p.getId().equals(productId));
        userRepo.save(user);
    }

    public void addToWishlist(Long productId, HttpServletRequest request) {
        AppUser user = currentUserService.getCurrentUser(request);
        Product product = productRepo.findById(productId)
                .orElseThrow(() -> new RuntimeException("Product not found"));
        user.getWishlist().add(product);
        userRepo.save(user);
    }

    public void removeFromWishlist(Long productId, HttpServletRequest request) {
        AppUser user = currentUserService.getCurrentUser(request);
        user.getWishlist().removeIf(p -> p.getId().equals(productId));
        userRepo.save(user);
    }
}
