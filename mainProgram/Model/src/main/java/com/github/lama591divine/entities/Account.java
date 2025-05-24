package com.github.lama591divine.entities;

import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Entity
@Getter @Setter @NoArgsConstructor
@Table(name = "accounts")
public class Account {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "account_number", nullable = false, unique = true, updatable = false)
    private UUID accountNumber;

    @Column(nullable = false)
    private Integer balance = 0;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    private User owner;

    @OneToMany(mappedBy = "account", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Transaction> transactions = new ArrayList<>();

    public Account(UUID accountNumber, Integer balance, User owner) {
        this.accountNumber = accountNumber;
        this.balance       = balance;
        this.owner         = owner;
    }

    public void addTransaction(Transaction tx) {
        transactions.add(tx);
        tx.setAccount(this);
    }
}

