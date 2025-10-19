package com.challenge.securetransfer.service;

import com.challenge.securetransfer.dto.LoginRequest;
import com.challenge.securetransfer.dto.LoginResponse;
import com.challenge.securetransfer.model.User;
import com.challenge.securetransfer.repository.UserRepository;
import com.challenge.securetransfer.security.JwtService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;


@Service @RequiredArgsConstructor
public class AuthService {
  private final UserRepository userRepo;
  private final PasswordEncoder encoder;
  private final JwtService jwt;

  public LoginResponse login(LoginRequest req) {
    User u = userRepo.findByUsername(req.username())
      .orElseThrow(() -> new RuntimeException("Bad credentials"));
    if (!encoder.matches(req.password(), u.getPassword()))
      throw new RuntimeException("Bad credentials");
    return new LoginResponse(jwt.generateToken(u.getUsername()));
  }
}
