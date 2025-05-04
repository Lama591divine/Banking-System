package com.github.lama591divine.service;

import com.github.lama591divine.dao.AccountDao;
import com.github.lama591divine.dao.UserDao;
import com.github.lama591divine.dto.response.AccountDto;
import com.github.lama591divine.entitys.Account;
import com.github.lama591divine.entitys.User;
import com.github.lama591divine.exception.*;
import com.github.lama591divine.mapper.DtoMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class AccountService {

    private final AccountDao accountDao;
    private final UserDao userDao;
    private final DtoMapper mapper;

    @Transactional
    public void createAccountForUser(String login) {
        User owner = userDao.findById(login).orElseThrow(() -> new UserNotFoundException(login));
        Account account = new Account(UUID.randomUUID().toString(), 0, owner, new ArrayList<>());
        accountDao.save(account);
    }

    public AccountDto get(String id) {
        return mapper.toDto(find(id));
    }

    public List<String> getTransactionsByFilter(String id, String typeTransaction) {
        return mapper.toDto(find(id)).transactions().stream().filter((tx -> tx.contains(typeTransaction))).collect(Collectors.toList());
    }

    public List<AccountDto> getAll() {
        List<Account> accounts = accountDao.findAll();
        return accounts.stream().map(mapper::toDto).collect(Collectors.toList());
    }

    @Transactional
    public AccountDto deposit(String id, int amount) {
        Account account = find(id);
        account.setBalance(account.getBalance() + amount);
        account.getTransactions().add("Deposit +" + amount);
        return mapper.toDto(accountDao.save(account));
    }

    @Transactional
    public AccountDto withdraw(String id, int amount) {
        Account account = find(id);

        if (account.getBalance() < amount) {
            throw new ValidationException("Insufficient funds");
        }

        account.setBalance(account.getBalance() - amount);
        account.getTransactions().add("Withdraw -" + amount);
        return mapper.toDto(accountDao.save(account));
    }

    @Transactional
    public void transfer(String fromId, String toId, int amount) {
        Account sender = find(fromId);
        Account receiver = find(toId);

        int commission = calculateCommission(amount, sender.getOwner(), receiver.getOwner());
        int total = amount + commission;

        if (sender.getBalance() < total) {
            throw new ValidationException("Insufficient funds (with commission)");
        }

        sender.setBalance(sender.getBalance() - total);
        sender.getTransactions().add("Transfer -" + amount + " (commission " + commission + ") -> " + toId);

        receiver.setBalance(receiver.getBalance() + amount);
        receiver.getTransactions().add("Receive +" + amount + " <- " + fromId);

        accountDao.save(sender);
        accountDao.save(receiver);
    }

    private Account find(String id) {
        return accountDao.findById(id).orElseThrow(() -> new AccountNotFoundException(id));
    }

    private int calculateCommission(int amount, User from, User to) {
        if (from.equals(to)) {
            return 0;
        }
        return (int) (amount * (from.getFriends().contains(to) ? 0.03 : 0.10));
    }
}
