package com.github.Lama591divine;

import java.util.ArrayList;
import java.util.UUID;


public class AccountService {
    private final Dao<User> userDao;
    private final Dao<Account> accountDao;

    public AccountService(Dao<User> userDao, Dao<Account> accountDao) {
        this.userDao = userDao;
        this.accountDao = accountDao;
    }

    public void createAccount(String login) {
        User user = userDao.getObjectById(login);
        if (user == null) {
            System.out.println("User not found.");
            return;
        }
        Account newAccount = new Account(
                UUID.randomUUID().toString(),
                0,
                new ArrayList<>(),
                user.getLogin()
        );

        accountDao.add(newAccount);
        System.out.println("Account created successfully with ID: " + newAccount.getId());
    }

    public void showBalance(String accountId) {
        Account account = accountDao.getObjectById(accountId);
        if (account == null) {
            System.out.println("Account not found.");
            return;
        }
        System.out.println("Account Balance: " + account.getBalance());
    }

    public void depositMoney(String accountId, int amount) {
        Account account = accountDao.getObjectById(accountId);
        if (account == null) {
            System.out.println("Account not found.");
            return;
        }
        if (amount <= 0) {
            System.out.println("Deposit error: amount must be positive");
            return;
        }

        int newBalance = account.getBalance() + amount;
        account.setBalance(newBalance);

        account.getTransactions().add(
                "Deposit: +" + amount + " | New balance: " + newBalance
        );

        System.out.println("Deposit successful.");
    }

    public void withdrawMoney(String accountId, int amount) {
        Account account = accountDao.getObjectById(accountId);
        if (account == null) {
            System.out.println("Account not found.");
            return;
        }
        if (amount <= 0) {
            System.out.println("Invalid withdrawal amount.");
            return;
        }
        if (account.getBalance() < amount) {
            System.out.println("Withdrawal error: insufficient funds");
            return;
        }

        int newBalance = account.getBalance() - amount;
        account.setBalance(newBalance);

        account.getTransactions().add(
                "Withdrawal: -" + amount + " | New balance: " + newBalance
        );

        System.out.println("Withdrawal successful.");
    }

    public void transferMoney(String senderAccountId, String receiverAccountId, int amount) {
        Account sender = accountDao.getObjectById(senderAccountId);
        Account receiver = accountDao.getObjectById(receiverAccountId);

        if (sender == null) {
            System.out.println("Sender account not found.");
            return;
        }
        if (receiver == null) {
            System.out.println("Receiver account not found.");
            return;
        }
        if (amount <= 0) {
            System.out.println("Transfer error: amount must be positive");
            return;
        }

        User senderUser = userDao.getObjectById(sender.getOwner());
        User receiverUser = userDao.getObjectById(receiver.getOwner());
        double commissionRate = getCommissionRate(senderUser, receiverUser);

        int commission = (int) (amount * commissionRate);
        int totalAmount = amount + commission;

        if (sender.getBalance() < totalAmount) {
            System.out.println("Transfer error: insufficient funds (including commission).");
            return;
        }

        sender.setBalance(sender.getBalance() - totalAmount);
        sender.getTransactions().add(
                "Transfer: -" + amount + " (commission " + commission + ") to " + receiver.getId()
                        + " | Balance: " + sender.getBalance()
        );

        receiver.setBalance(receiver.getBalance() + amount);
        receiver.getTransactions().add(
                "Received: +" + amount + " from " + sender.getId()
                        + " | Balance: " + receiver.getBalance()
        );

        System.out.println("Transfer successful.");
    }

    private double getCommissionRate(User from, User to) {
        if (from.getLogin().equals(to.getLogin())) {
            return 0.0;
        }
        if (from.getFriends().contains(to.getLogin())) {
            return 0.03;
        }
        return 0.1;
    }
}
