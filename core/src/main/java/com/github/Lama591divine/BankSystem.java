package com.github.Lama591divine;

import com.github.Lama591divine.entities.Account;
import com.github.Lama591divine.entities.Gender;
import com.github.Lama591divine.entities.HairColor;
import com.github.Lama591divine.entities.User;
import com.github.Lama591divine.interfaces.Repository;

import java.util.UUID;

/**
 * The {@code BankSystem} class manages users, accounts, and financial transactions
 * such as deposits, withdrawals, and transfers.
 */
public class BankSystem {
    private final Repository<User> userRepository;
    private final Repository<Account> accountRepository;

    /**
     * Constructs a {@code BankSystem} with user and account repositories.
     *
     * @param userRepository   the repository for managing users
     * @param accountRepository the repository for managing accounts
     */
    public BankSystem(Repository<User> userRepository, Repository<Account> accountRepository) {
        this.userRepository = userRepository;
        this.accountRepository = accountRepository;
    }

    /**
     * Creates a new user and stores it in the repository.
     *
     * @param login       the unique login of the user
     * @param name        the name of the user
     * @param age         the age of the user
     * @param genderStr   the gender of the user as a string
     * @param hairColorStr the hair color of the user as a string
     */
    public void createUser(String login, String name, int age, String genderStr, String hairColorStr) {
        Gender gender = Gender.valueOf(genderStr.toUpperCase());
        HairColor hairColor = HairColor.valueOf(hairColorStr.toUpperCase());

        User user = new User(login, name, age, gender, hairColor);
        userRepository.add(user);

        System.out.println("User created successfully!");
    }

    /**
     * Displays information about a user.
     *
     * @param login the login of the user
     */
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

    /**
     * Manages a user's friends list by adding or removing a friend.
     *
     * @param login       the login of the user performing the action
     * @param friendLogin the login of the friend to add or remove
     * @param action      "add" to add a friend, "remove" to remove a friend
     */
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

    /**
     * Creates a new bank account for a user.
     *
     * @param login the login of the user for whom the account is created
     */
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

    /**
     * Displays the balance of an account.
     *
     * @param accountId the ID of the account
     */
    public void showBalance(String accountId) {
        Account account = accountRepository.getObject(accountId);
        if (account == null) {
            System.out.println("Account not found.");
            return;
        }

        System.out.println("Account Balance: " + account.getBalance());
    }

    /**
     * Deposits money into an account.
     *
     * @param accountId the ID of the account
     * @param amount    the amount to deposit
     */
    public void depositMoney(String accountId, int amount) {
        Account account = accountRepository.getObject(accountId);
        if (account == null) {
            System.out.println("Account not found.");
            return;
        }

        account.addBalance(amount);
        System.out.println("Deposit successful.");
    }

    /**
     * Withdraws money from an account if the balance is sufficient.
     *
     * @param accountId the ID of the account
     * @param amount    the amount to withdraw
     * @throws RuntimeException if the account is not found, the amount is invalid, or there are insufficient funds
     */
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

    /**
     * Transfers money between two accounts if the sender has sufficient funds.
     *
     * @param senderAccountId   the ID of the sender's account
     * @param receiverAccountId the ID of the receiver's account
     * @param amount            the amount to transfer
     */
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
