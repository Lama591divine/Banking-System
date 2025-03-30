package com.github.Lama591divine;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class Account {
    private final String id;
    private int balance;
    private final String owner;
    private final List<String> transactions;

    public Account(String id, int balance, List<String> transactions, String owner) {
        this.id = id;
        this.balance = balance;
        this.owner = owner;
        this.transactions = transactions;
    }
}
