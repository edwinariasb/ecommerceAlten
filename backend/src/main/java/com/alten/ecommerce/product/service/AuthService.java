package com.alten.ecommerce.product.service;

import com.alten.ecommerce.product.back.entity.AppUser;
import com.alten.ecommerce.product.back.repo.AppUserRepository;
import com.alten.ecommerce.product.dto.LoginRequest;
import com.alten.ecommerce.product.dto.RegisterRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthService {

    @Autowired
    private AppUserRepository userRepo;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private JwtService jwtService;

    public void register(RegisterRequest req) {
        AppUser user = new AppUser();
        user.setEmail(req.email());
        user.setUsername(req.username());
        user.setFirstname(req.firstname());
        user.setPassword(passwordEncoder.encode(req.password()));
        userRepo.save(user);
    }

    public String login(LoginRequest req) {
        AppUser user = userRepo.findByEmail(req.email())
                .orElseThrow(() -> new RuntimeException("User not found"));

        if (!passwordEncoder.matches(req.password(), user.getPassword())) {
            throw new RuntimeException("Bad credentials");
        }

        // on génère un JWT
        return jwtService.generateToken(user.getEmail());
    }
}