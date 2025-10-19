package com.challenge.securetransfer.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class TransferRequest {
    private String recipientAccount;
    private double amount;
}
