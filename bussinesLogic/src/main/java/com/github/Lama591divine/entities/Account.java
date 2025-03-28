package com.github.Lama591divine.entities;

import lombok.Getter;

import java.util.ArrayList;

/**
 * The {@code Account} class represents a bank account with an owner, balance, and transaction history.
 * It allows deposits, withdrawals, and transfers between accounts with an applicable commission.
 */
@Getter
public class Account {

    private final String id;
    private int balance;
    private final String owner;
    private final ArrayList<String> transactions;

    /**
     * @param id    the unique identifier of the account
     * @param owner the owner of the account
     */
    public Account(String id, int balance, ArrayList<String> transactions, String owner) {
        this.id = id;
        this.balance = balance;
        this.owner = owner;
        this.transactions = transactions;
    }

    public ArrayList<String> getTransactions() {
        return new ArrayList<>(transactions);
    }

    /**
     * Adds the specified amount to the account balance.
     * If the amount is not positive, a transaction error is logged.
     *
     * @param money the amount to be added
     */
    public void addBalance(int money) {
        if (money > 0) {
            this.balance += money;
            transactions.add("Deposit: +" + money + " | New balance: " + balance);
        } else {
            transactions.add("Deposit error: amount must be positive");
        }
    }

    /**
     * Withdraws the specified amount from the account if sufficient funds are available.
     * If the withdrawal is not possible, a transaction error is logged.
     *
     * @param money the amount to be withdrawn
     */
    public void withDraw(int money) {
        if (money > 0 && balance >= money) {
            this.balance -= money;
            transactions.add("Withdrawal: -" + money + " | New balance: " + balance);
        }
        else {
            transactions.add("Withdrawal error: insufficient funds");
        }
    }

    /**
     * Transfers the specified amount to another account, considering commission fees.
     * If the transfer is not possible due to insufficient funds or invalid amount, an error is logged.
     *
     * @param to     the recipient account
     * @param amount the amount to transfer
     * @return {@code true} if the transfer was successful, {@code false} otherwise
     */
    public boolean transfer(Account to, int amount, double commissionRate) {
        if (amount <= 0) {
            transactions.add("Transfer error: amount must be positive");
            return false;
        }
        if (this.balance < amount) {
            transactions.add("Transfer error: insufficient funds");
            return false;
        }

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
}
