package com.github.Lama591divine.systems;

import com.github.Lama591divine.ConverterDTO;
import com.github.Lama591divine.DTO.DtoAccount;
import com.github.Lama591divine.DTO.DtoUser;
import com.github.Lama591divine.entities.Account;
import com.github.Lama591divine.entities.User;
import com.github.Lama591divine.repositoryes.Repository;

import java.util.ArrayList;
import java.util.UUID;

public class AccountSystem extends ConverterDTO {
    private final Repository<DtoUser> userRepository;
    private final Repository<DtoAccount> accountsRepository;

    public AccountSystem(Repository<DtoUser> userRepository, Repository<DtoAccount> accountsRepository) {
        this.accountsRepository = accountsRepository;
        this.userRepository = userRepository;
    }

    public void createAccount(String login) {
        DtoUser dtoUser = userRepository.getObject(login);
        if (dtoUser == null) {
            System.out.println("User not found.");
            return;
        }

        DtoAccount dtoAccount = new DtoAccount(UUID.randomUUID().toString(), 0, new ArrayList<>(), dtoUser.getLogin());
        accountsRepository.add(dtoAccount);
        System.out.println("Account created successfully with ID: " + dtoAccount.getId());
    }

    public void showBalance(String accountId) {
        DtoAccount dtoAccount = accountsRepository.getObject(accountId);
        if (dtoAccount == null) {
            System.out.println("Account not found.");
            return;
        }

        System.out.println("Account Balance: " + dtoAccount.getBalance());
    }

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

    public void withdrawMoney(String accountId, int amount) {
        DtoAccount dtoAccount = accountsRepository.getObject(accountId);

        if (dtoAccount == null) {
            System.out.println("Account not found.");
        }

        if (amount <= 0) {
            System.out.println("Invalid withdrawal amount.");
        }

        if (dtoAccount.getBalance() < amount) {
            System.out.println("Withdrawal error: insufficient funds");
        }

        Account account = toAccount(dtoAccount);
        account.withDraw(amount);

        accountsRepository.remove(dtoAccount);
        dtoAccount = toDtoAccount(account);
        accountsRepository.add(dtoAccount);
        System.out.println("Withdrawal successful.");
    }

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

    private double getCommisionRate(User from, User to) {
        if (from.equals(to)) return 0.0;
        if (from.getFriends().contains(to.getLogin())) return 0.03;
        return 0.1;
    }
}
