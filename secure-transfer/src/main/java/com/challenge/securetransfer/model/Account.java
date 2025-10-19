package com.challenge.securetransfer.model;

import jakarta.persistence.*;
import lombok.*;

@Entity @Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
@Table(name="accounts")
public class Account {
  @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Column(name="account_number", unique = true, nullable = false)
  private String accountNumber;

  @Column(nullable = false)
  private Double balance;

  @OneToOne(optional = false)
  private User user;
}
