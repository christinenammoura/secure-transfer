package com.challenge.securetransfer.controller;

import com.challenge.securetransfer.dto.AccountMeResponse;
import com.challenge.securetransfer.model.Account;
import com.challenge.securetransfer.model.User;
import com.challenge.securetransfer.repository.AccountRepository;
import com.challenge.securetransfer.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@CrossOrigin(origins = "http://localhost:4200", allowCredentials = "true")

@RestController @RequestMapping("/api/accounts") @RequiredArgsConstructor
public class AccountController {
  private final UserRepository userRepo;
  private final AccountRepository accountRepo;

  @GetMapping("/me")
  public AccountMeResponse me(Authentication auth) {
    String username = auth.getName();  
    User u = userRepo.findByUsername(username).orElseThrow();
    Account a = accountRepo.findByUser(u).orElseThrow();
    return new AccountMeResponse(u.getUsername(), a.getAccountNumber(), a.getBalance());
}


}
