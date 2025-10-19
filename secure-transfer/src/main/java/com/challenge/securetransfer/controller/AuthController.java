package com.challenge.securetransfer.controller;

import com.challenge.securetransfer.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import com.challenge.securetransfer.model.User;


import java.util.Map;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {

    private final com.challenge.securetransfer.service.AuthService authService;
    private final UserRepository userRepo;

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody com.challenge.securetransfer.dto.LoginRequest req) {
        return ResponseEntity.ok(authService.login(req));
    }

    @GetMapping("/me")
    public ResponseEntity<?> getCurrentUser(@AuthenticationPrincipal User user) {
        if (user == null) {
            return ResponseEntity.status(401).body(Map.of("error", "Not authenticated"));
        }

        return ResponseEntity.ok(Map.of(
            "username", user.getUsername(),
            "balance", user.getAccount() != null ? user.getAccount().getBalance() : 0
        ));
    }

}
