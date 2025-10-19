package com.challenge.securetransfer.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.challenge.securetransfer.model.Account;
import com.challenge.securetransfer.model.User;

import java.util.Optional;

public interface AccountRepository extends JpaRepository<Account, Long> {
  Optional<Account> findByAccountNumber(String accountNumber);
  Optional<Account> findByUser(User user);
}
