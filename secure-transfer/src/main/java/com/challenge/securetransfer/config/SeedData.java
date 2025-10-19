package com.challenge.securetransfer.config;

import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.challenge.securetransfer.model.Account;
import com.challenge.securetransfer.model.User;
import com.challenge.securetransfer.repository.AccountRepository;
import com.challenge.securetransfer.repository.UserRepository;

@Configuration @RequiredArgsConstructor
public class SeedData {
  private final UserRepository userRepo;
  private final AccountRepository accRepo;
  private final PasswordEncoder enc;

  @Bean
  CommandLineRunner runner() {
    return args -> {
      if (userRepo.count() == 0) {
        User u1 = userRepo.save(User.builder().username("alice").password(enc.encode("alice123")).build());
        User u2 = userRepo.save(User.builder().username("bob").password(enc.encode("bob123")).build());
        accRepo.save(Account.builder().user(u1).accountNumber("ACC1001").balance(1000.0).build());
        accRepo.save(Account.builder().user(u2).accountNumber("ACC2002").balance(500.0).build());
      }
    };
  }
}
