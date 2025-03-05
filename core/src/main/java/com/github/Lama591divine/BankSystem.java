package com.github.Lama591divine;

import com.github.Lama591divine.entities.Account;
import com.github.Lama591divine.entities.Gender;
import com.github.Lama591divine.entities.HairColor;
import com.github.Lama591divine.entities.User;
import com.github.Lama591divine.interfaces.Repository;

import java.util.UUID;

public class BankSystem {
    private final Repository<User> userRepository;
    private final Repository<Account> accountRepository;

    public BankSystem(Repository<User> userRepository, Repository<Account> accountRepository) {
        this.userRepository = userRepository;
        this.accountRepository = accountRepository;
    }

    public void createUser(String login, String name, int age, String genderStr, String hairColorStr) {
        Gender gender = Gender.valueOf(genderStr.toUpperCase());
        HairColor hairColor = HairColor.valueOf(hairColorStr.toUpperCase());

        User user = new User(login, name, age, gender, hairColor);
        userRepository.add(user);

        System.out.println("User created successfully!");
    }

    public void showUserInfo(String login) {
        User user = userRepository.getObject(login);
        if (user == null) {
            System.out.println("User not found.");
            return;
        }

        System.out.println("\nUser Info:");
        System.out.println("Login: " + user.getLogin());
        System.out.println("Name: " + user.getName());
        System.out.println("Age: " + user.getAge());
        System.out.println("Gender: " + user.getGender());
        System.out.println("Hair Color: " + user.getHairColor());

        System.out.println("Friends:");
        user.showFriends();
    }

    public void manageFriends(String login, String friendLogin, String action) {
        User user = userRepository.getObject(login);
        User friend = userRepository.getObject(friendLogin);

        if (user == null || friend == null) {
            System.out.println("User or friend not found.");
            return;
        }

        if (action.equalsIgnoreCase("add")) {
            user.addFriend(friend);
            System.out.println(friendLogin + " added as a friend.");
        } else if (action.equalsIgnoreCase("remove")) {
            user.removeFriend(friend);
            System.out.println(friendLogin + " removed from friends.");
        } else {
            System.out.println("Invalid action.");
        }
    }

    public void createAccount(String login) {
        User user = userRepository.getObject(login);
        if (user == null) {
            System.out.println("User not found.");
            return;
        }

        Account account = new Account(UUID.randomUUID().toString(), user);
        accountRepository.add(account);
        System.out.println("Account created successfully with ID: " + account.getId());
    }

    public void showBalance(String accountId) {
        Account account = accountRepository.getObject(accountId);
        if (account == null) {
            System.out.println("Account not found.");
            return;
        }

        System.out.println("Account Balance: " + account.getBalance());
    }

    public void depositMoney(String accountId, int amount) {
        Account account = accountRepository.getObject(accountId);
        if (account == null) {
            System.out.println("Account not found.");
            return;
        }

        account.addBalance(amount);
        System.out.println("Deposit successful.");
    }

    public void withdrawMoney(String accountId, int amount) {
        Account account = accountRepository.getObject(accountId);

        if (account == null) {
            throw new RuntimeException("Account not found.");
        }

        if (amount <= 0) {
            throw new RuntimeException("Invalid withdrawal amount.");
        }

        if (account.getBalance() < amount) {
            throw new RuntimeException("Withdrawal error: insufficient funds");
        }

        account.withDraw(amount);
        System.out.println("Withdrawal successful.");
    }


    public void transferMoney(String senderAccountId, String receiverAccountId, int amount) {
        Account senderAccount = accountRepository.getObject(senderAccountId);
        Account receiverAccount = accountRepository.getObject(receiverAccountId);

        if (senderAccount == null || receiverAccount == null) {
            System.out.println("Sender or receiver account not found.");
            return;
        }

        if (senderAccount.transfer(receiverAccount, amount)) {
            System.out.println("Transfer successful.");
        } else {
            System.out.println("Transfer failed.");
        }
    }
}
