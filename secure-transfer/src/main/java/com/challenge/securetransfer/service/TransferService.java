package com.challenge.securetransfer.service;

import com.challenge.securetransfer.model.Account;
import com.challenge.securetransfer.model.User;
import com.challenge.securetransfer.repository.AccountRepository;
import com.challenge.securetransfer.repository.UserRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class TransferService {

    private final UserRepository userRepo;
    private final AccountRepository accountRepo;

    @Transactional
public void transferFunds(String senderUsername, String recipientAccount, double amount) {
    User sender = userRepo.findByUsername(senderUsername)
            .orElseThrow(() -> new RuntimeException("Sender not found"));
    Account senderAcc = accountRepo.findByUser(sender)
            .orElseThrow(() -> new RuntimeException("Sender account not found"));
    Account recipientAcc = accountRepo.findByAccountNumber(recipientAccount)
            .orElseThrow(() -> new RuntimeException("Recipient account not found"));

    if (amount <= 0)
        throw new RuntimeException("Invalid transfer amount");
    if (senderAcc.getAccountNumber().equals(recipientAccount))
        throw new RuntimeException("Cannot transfer to your own account");
    if (senderAcc.getBalance() < amount)
        throw new RuntimeException("Insufficient funds");

    senderAcc.setBalance(senderAcc.getBalance() - amount);
    recipientAcc.setBalance(recipientAcc.getBalance() + amount);

    accountRepo.save(senderAcc);
    accountRepo.save(recipientAcc);

System.out.println("Sender: " + sender.getUsername());
System.out.println("Recipient: " + recipientAccount);
System.out.println("Amount: " + amount);

}

}
