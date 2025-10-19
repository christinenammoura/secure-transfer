package com.challenge.securetransfer.security;

import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import java.util.Date;

@Service
public class JwtService {
  @Value("${app.jwt.secret}") private String secret;
  @Value("${app.jwt.expiration-ms}") private long expirationMs;

  public String generateToken(String username) {
    Date now = new Date();
    Date exp = new Date(now.getTime() + expirationMs);
    return Jwts.builder()
      .setSubject(username)
      .setIssuedAt(now)
      .setExpiration(exp)
      .signWith(Keys.hmacShaKeyFor(secret.getBytes()), SignatureAlgorithm.HS256)
      .compact();
  }

  public String extractUsername(String token) {
    return Jwts.parserBuilder()
      .setSigningKey(Keys.hmacShaKeyFor(secret.getBytes()))
      .build()
      .parseClaimsJws(token)
      .getBody()
      .getSubject();
  }
}
