package com.challenge.securetransfer.controller;

import com.challenge.securetransfer.dto.TransferRequest;
import com.challenge.securetransfer.service.TransferService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/transfers")
@RequiredArgsConstructor
@CrossOrigin(origins = "http://localhost:4200", allowCredentials = "true")
public class TransferController {

    private final TransferService transferService;

    @PostMapping
    public ResponseEntity<?> transfer(@RequestBody TransferRequest req, Authentication auth) {
        try {
            String senderUsername = auth.getName();
            transferService.transferFunds(senderUsername, req.getRecipientAccount(), req.getAmount());
            return ResponseEntity.ok(Map.of("message", "Transfer successful"));
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(Map.of("error", e.getMessage()));
        }
    }
}
