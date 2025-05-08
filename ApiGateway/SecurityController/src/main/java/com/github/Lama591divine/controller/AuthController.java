package com.github.Lama591divine.controller;

import com.github.Lama591divine.User;
import com.github.Lama591divine.service.AuthService;
import com.github.Lama591divine.service.serviceDto.AuthRequest;
import com.github.Lama591divine.service.serviceDto.TokenResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    @PostMapping("/login")
    public TokenResponse login(@RequestBody @Valid AuthRequest req) {
        return authService.login(req);
    }

    @PostMapping("/admin/users")
    @PreAuthorize("hasRole('ADMIN')")
    public User createClient(@RequestBody @Valid User u) {
        return authService.createClient(u);
    }

    @PostMapping("/admin/admins")
    @PreAuthorize("hasRole('ADMIN')")
    public User createAdmin(@RequestBody @Valid User u) {
        return authService.createAdmin(u);
    }

    @GetMapping("/admin/users")
    @PreAuthorize("hasRole('ADMIN')")
    public List<User> all() {
        return authService.findAllUsers();
    }

    @GetMapping("/admin/users/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public User byId(@PathVariable Long id) {
        return authService.findUserById(id);
    }
}

