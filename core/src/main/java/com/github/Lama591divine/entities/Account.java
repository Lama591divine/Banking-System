package com.github.Lama591divine.entities;

import java.util.ArrayList;

public class Account {

    private String id;
    private int balance;
    private User owner;
    private ArrayList<String> transactions;

    public Account(String id, User owner) {
        this.id = id;
        this.balance = 0;
        this.owner = owner;
        transactions = new ArrayList<>();
    }

    public ArrayList<String> getTransactions() {
        return new ArrayList<>(transactions);
    }

    public String getId() {
        return id;
    }

    public int getBalance() {
        return balance;
    }

    public User getOwner() {
        return owner;
    }

    public void addBalance(int money) {
        if (money > 0) {
            this.balance += money;
            transactions.add("Deposit: +" + money + " | New balance: " + balance);
        } else {
            transactions.add("Deposit error: amount must be positive");
        }
    }

    public void withDraw(int money) {
        if (money > 0 && balance >= money) {
            this.balance -= money;
            transactions.add("Withdrawal: -" + money + " | New balance: " + balance);
        } else {
            transactions.add("Withdrawal error: insufficient funds");
        }
    }

    public boolean transfer(Account to, int amount) {
        if (amount <= 0) {
            transactions.add("Transfer error: amount must be positive");
            return false;
        }
        if (this.balance < amount) {
            transactions.add("Transfer error: insufficient funds");
            return false;
        }

        double commissionRate = getCommissionRate(this.owner, to.owner);
        int commission = (int) (amount * commissionRate);
        int totalAmount = amount + commission;

        if (this.balance < totalAmount) {
            transactions.add("Transfer error: insufficient funds including commission");
            return false;
        }

        this.balance -= totalAmount;
        to.balance += amount;

        this.transactions.add("Transfer: -" + amount + " (commission " + commission + ") to account " + to.id + " | Balance: " + balance);
        to.transactions.add("Received: +" + amount + " from account " + this.id + " | Balance: " + to.balance);

        return true;
    }

    private double getCommissionRate(User from, User to) {
        if (from.equals(to)) return 0.0;
        if (from.getFriends().contains(to)) return 0.03;
        return 0.1;
    }
}
