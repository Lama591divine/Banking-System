package com.github.Lama591divine;

import com.github.Lama591divine.entities.Account;
import com.github.Lama591divine.entities.Gender;
import com.github.Lama591divine.entities.HairColor;
import com.github.Lama591divine.entities.User;
import com.github.Lama591divine.interfaces.AccountRepository;
import com.github.Lama591divine.interfaces.UserRepository;

import java.util.Scanner;
import java.util.UUID;

public class BankSystem {
    public final Scanner scanner;
    private final UserRepository userRepository;
    private final AccountRepository accountRepository;

    public BankSystem(UserRepository userRepository, AccountRepository accountRepository) {
        scanner = new Scanner(System.in);
        this.userRepository = userRepository;
        this.accountRepository = accountRepository;
    }

    public void createUser() {
        System.out.print("Enter login: ");
        String login = scanner.nextLine();

        System.out.print("Enter name: ");
        String name = scanner.nextLine();

        System.out.print("Enter age: ");
        int age = scanner.nextInt();
        scanner.nextLine();

        System.out.print("Enter gender (MALE/FEMALE): ");
        Gender gender = Gender.valueOf(scanner.nextLine().toUpperCase());

        System.out.print("Enter hair color (BLACK, BROWN, BLONDE, RED, GREY, WHITE, OTHER): ");
        HairColor hairColor = HairColor.valueOf(scanner.nextLine().toUpperCase());

        User user = new User(login, name, age, gender, hairColor);
        userRepository.add(user);

        System.out.println("User created successfully!");
    }

    public void showUserInfo() {
        System.out.print("Enter user login: ");
        String login = scanner.nextLine();
        User user = userRepository.getUser(login);

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

    public void manageFriends() {
        System.out.print("Enter your login: ");
        String login = scanner.nextLine();
        User user = userRepository.getUser(login);

        if (user == null) {
            System.out.println("User not found.");
            return;
        }

        System.out.print("Enter friend's login: ");
        String friendLogin = scanner.nextLine();
        User friend = userRepository.getUser(friendLogin);

        if (friend == null) {
            System.out.println("Friend not found.");
            return;
        }

        System.out.print("Add or remove friend? (add/remove): ");
        String action = scanner.nextLine();

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

    public void createAccount() {
        System.out.print("Enter user login: ");
        String login = scanner.nextLine();
        User user = userRepository.getUser(login);

        if (user == null) {
            System.out.println("User not found.");
            return;
        }

        Account account = new Account(UUID.randomUUID().toString(), user);
        accountRepository.add(account);
        System.out.println("Account created successfully with ID: " + account.getId());
    }

    public void showBalance() {
        Account account = findAccount();
        if (account != null) {
            System.out.println("Account Balance: " + account.getBalance());
        }
    }

    public void depositMoney() {
        Account account = findAccount();
        if (account == null) return;

        System.out.print("Enter deposit amount: ");
        int amount = scanner.nextInt();
        scanner.nextLine();

        account.addBalance(amount);
        System.out.println("Deposit successful.");
    }

    public void withdrawMoney() {
        Account account = findAccount();
        if (account == null) return;

        System.out.print("Enter withdrawal amount: ");
        int amount = scanner.nextInt();
        scanner.nextLine();

        account.withDraw(amount);
        System.out.println("Withdrawal successful.");
    }

    public void transferMoney() {
        System.out.print("Enter sender account ID: ");
        String senderAccountId = scanner.nextLine();
        Account senderAccount = accountRepository.getAccount(senderAccountId);

        if (senderAccount == null) {
            System.out.println("Sender account not found.");
            return;
        }

        System.out.print("Enter receiver account ID: ");
        String receiverAccountId = scanner.nextLine();
        Account receiverAccount = accountRepository.getAccount(receiverAccountId);

        if (receiverAccount == null) {
            System.out.println("Receiver account not found.");
            return;
        }

        System.out.print("Enter transfer amount: ");
        int amount = scanner.nextInt();
        scanner.nextLine();

        if (senderAccount.transfer(receiverAccount, amount)) {
            System.out.println("Transfer successful.");
        } else {
            System.out.println("Transfer failed.");
        }
    }

    public Account findAccount() {
        System.out.print("Enter account ID: ");
        String accountId = scanner.nextLine();
        Account account = accountRepository.getAccount(accountId);

        if (account == null) {
            System.out.println("Account not found.");
            return null;
        }

        return account;
    }
}
