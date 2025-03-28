package com.github.Lama591divine.systems;

import com.github.Lama591divine.ConverterDTO;
import com.github.Lama591divine.DTO.DtoAccount;
import com.github.Lama591divine.DTO.DtoUser;
import com.github.Lama591divine.entities.Account;
import com.github.Lama591divine.entities.User;
import com.github.Lama591divine.repositoryes.Repository;

import java.util.ArrayList;
import java.util.UUID;

/**
 * The {@code AccountSystem} class manages user accounts, including creation, balance display,
 * deposits, withdrawals, and transfers.
 */
public class AccountSystem extends ConverterDTO {
    private final Repository<DtoUser> userRepository;
    private final Repository<DtoAccount> accountsRepository;

    /**
     * Constructs an {@code AccountSystem} with user and account repositories.
     *
     * @param userRepository    the repository managing users
     * @param accountsRepository the repository managing accounts
     */
    public AccountSystem(Repository<DtoUser> userRepository, Repository<DtoAccount> accountsRepository) {
        this.accountsRepository = accountsRepository;
        this.userRepository = userRepository;
    }

    /**
     * Creates a new account for the given user login.
     *
     * @param login the login of the user
     */
    public void createAccount(String login) {
        DtoUser dtoUser = userRepository.getObject(login);
        if (dtoUser == null) {
            System.out.println("User not found.");
            return;
        }

        DtoAccount dtoAccount = new DtoAccount(UUID.randomUUID().toString(), 0, new ArrayList<>(), dtoUser.login());
        accountsRepository.add(dtoAccount);
        System.out.println("Account created successfully with ID: " + dtoAccount.id());
    }

    /**
     * Displays the balance of an account.
     *
     * @param accountId the ID of the account
     */
    public void showBalance(String accountId) {
        DtoAccount dtoAccount = accountsRepository.getObject(accountId);
        if (dtoAccount == null) {
            System.out.println("Account not found.");
            return;
        }

        System.out.println("Account Balance: " + dtoAccount.balance());
    }

    /**
     * Deposits money into an account.
     *
     * @param accountId the ID of the account
     * @param amount    the amount to deposit
     */
    public void depositMoney(String accountId, int amount) {
        DtoAccount dtoAccount = accountsRepository.getObject(accountId);
        if (dtoAccount == null) {
            System.out.println("Account not found.");
            return;
        }

        Account account = toAccount(dtoAccount);

        account.addBalance(amount);

        accountsRepository.remove(dtoAccount);
        dtoAccount = toDtoAccount(account);
        accountsRepository.add(dtoAccount);
        System.out.println("Deposit successful.");
    }

    /**
     * Withdraws money from an account.
     *
     * @param accountId the ID of the account
     * @param amount    the amount to withdraw
     */
    public void withdrawMoney(String accountId, int amount) {
        DtoAccount dtoAccount = accountsRepository.getObject(accountId);

        if (dtoAccount == null) {
            System.out.println("Account not found.");
        }

        if (amount <= 0) {
            System.out.println("Invalid withdrawal amount.");
        }

        if (dtoAccount.balance() < amount) {
            System.out.println("Withdrawal error: insufficient funds");
        }

        Account account = toAccount(dtoAccount);
        account.withDraw(amount);

        accountsRepository.remove(dtoAccount);
        dtoAccount = toDtoAccount(account);
        accountsRepository.add(dtoAccount);
        System.out.println("Withdrawal successful.");
    }

    /**
     * Transfers money from one account to another.
     *
     * @param senderAccountId   the ID of the sender's account
     * @param receiverAccountId the ID of the receiver's account
     * @param amount            the amount to transfer
     */
    public void transferMoney(String senderAccountId, String receiverAccountId, int amount) {
        DtoAccount senderAccount = accountsRepository.getObject(senderAccountId);
        DtoAccount receiverAccount = accountsRepository.getObject(receiverAccountId);

        if (senderAccount == null) {
            System.out.println("Sender account not found.");
            return;
        }

        if (receiverAccount == null) {
            System.out.println("Receiver account not found.");
            return;
        }

        Account sender = toAccount(senderAccount);
        Account receiver = toAccount(receiverAccount);

        User senderUser = toUser(userRepository.getObject(sender.getOwner()));
        User receiverUser = toUser(userRepository.getObject(receiver.getOwner()));

        double commisionRate = getCommisionRate(senderUser, receiverUser);

        if (sender.transfer(receiver, amount, commisionRate)) {
            accountsRepository.remove(senderAccount);
            accountsRepository.remove(receiverAccount);
            accountsRepository.add(toDtoAccount(sender));
            accountsRepository.add(toDtoAccount(receiver));
            System.out.println("Transfer successful.");
        }
        else {
            System.out.println("Transfer failed.");
        }
    }

    /**
     * Calculates the commission rate for a transaction between two users.
     *
     * @param from the sender user
     * @param to   the receiver user
     * @return the commission rate as a decimal (e.g., 0.03 for 3%)
     */
    private double getCommisionRate(User from, User to) {
        if (from.equals(to)) return 0.0;
        if (from.getFriends().contains(to.getLogin())) return 0.03;
        return 0.1;
    }
}
