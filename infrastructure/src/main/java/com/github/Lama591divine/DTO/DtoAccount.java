package com.github.Lama591divine.DTO;

import java.util.ArrayList;

public class DtoAccount {
    private String id;
    private int balance;
    private String owner;
    private ArrayList<String> transactions;

    public DtoAccount(String id, int balance, ArrayList<String> transactions, String owner) {
        this.id = id;
        this.balance = balance;
        this.owner = owner;
        this.transactions = transactions;
    }

    public String getId() {
        return id;
    }

    public int getBalance() {
        return balance;
    }

    public String getOwner() {
        return owner;
    }

    public ArrayList<String> getTransactions() {
        return transactions;
    }
}
