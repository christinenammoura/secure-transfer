package com.challenge.securetransfer.security;

import com.challenge.securetransfer.repository.UserRepository;
import jakarta.servlet.*;
import jakarta.servlet.http.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;  // <-- add this
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.List;

@Component
public class JwtAuthFilter extends OncePerRequestFilter {
  @Autowired JwtService jwtService;
  @Autowired UserRepository userRepo;

  @Override
  protected void doFilterInternal(HttpServletRequest req, HttpServletResponse res, FilterChain chain)
    throws ServletException, IOException {

    // Skip authentication for auth endpoints
    if (req.getServletPath().startsWith("/api/auth")) {
        chain.doFilter(req, res);
        return;
    }

    String auth = req.getHeader("Authorization");
    if (auth != null && auth.startsWith("Bearer ")) {
      String token = auth.substring(7);
      try {
        String username = jwtService.extractUsername(token);

        if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {
          var user = userRepo.findByUsername(username).orElse(null);
          if (user != null) {
            //  Use Spring Security UserDetails as principal
            var springUser = org.springframework.security.core.userdetails.User
                    .withUsername(user.getUsername())
                    .password("") // password not needed here
                    .authorities(List.of()) // add roles/authorities if needed
                    .build();

            var authentication = new UsernamePasswordAuthenticationToken(
                    springUser,
                    null,
                    springUser.getAuthorities()
            );
            authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(req));
            SecurityContextHolder.getContext().setAuthentication(authentication);
          }
        }
      } catch (Exception ignored) {}
    }

    chain.doFilter(req, res);
  }
}
