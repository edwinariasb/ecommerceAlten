package com.alten.ecommerce.product.service;

import com.alten.ecommerce.product.back.entity.AppUser;
import com.alten.ecommerce.product.back.repo.AppUserRepository;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CurrentUserService {

    @Autowired
    private AppUserRepository userRepo;

    public AppUser getCurrentUser(HttpServletRequest request) {
        String email = (String) request.getAttribute("currentUserEmail");
        if (email == null) {
            throw new RuntimeException("Unauthenticated");
        }
        return userRepo.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found"));
    }
}