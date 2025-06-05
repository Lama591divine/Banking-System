package com.github.lama591divine.service;

import com.github.lama591divine.dao.AccountDao;
import com.github.lama591divine.dao.UserDao;
import com.github.lama591divine.dto.kafkadto.AccountDomainEvent;
import com.github.lama591divine.dto.response.AccountDto;
import com.github.lama591divine.entities.Account;
import com.github.lama591divine.entities.Transaction;
import com.github.lama591divine.entities.User;
import com.github.lama591divine.exception.*;
import com.github.lama591divine.mapper.DtoMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Service @RequiredArgsConstructor
@Transactional(readOnly = true)
public class AccountService {

    private final AccountDao accountDao;
    private final UserDao    userDao;
    private final DtoMapper  mapper;
    private final ApplicationEventPublisher events;   // <-- новое

    @Transactional
    public void createAccountForUser(String login) {
        User user = userDao.findByLogin(login)
                .orElseThrow(() -> new UserNotFoundException(login));

        Account acc = accountDao.save(new Account(UUID.randomUUID(), 0, user));

        events.publishEvent(new AccountDomainEvent(
                acc.getAccountNumber().toString(),
                "CREATED",
                mapper.toDto(acc)));
    }

    public AccountDto get(String accountNumber) {
        return mapper.toDto(find(accountNumber));
    }

    public List<AccountDto> getAll() {
        return accountDao.findAll()
                .stream()
                .map(mapper::toDto)
                .toList();
    }
    @Transactional
    public AccountDto deposit(String accNum, int amount) {
        Account acc = find(accNum);
        acc.setBalance(acc.getBalance() + amount);
        acc.addTransaction(new Transaction(acc, "DEPOSIT +" + amount));

        AccountDto dto = mapper.toDto(acc);
        events.publishEvent(new AccountDomainEvent(accNum, "DEPOSIT", dto));
        return dto;
    }

    @Transactional
    public AccountDto withdraw(String accNum, int amount) {
        Account acc = find(accNum);
        if (acc.getBalance() < amount) throw new ValidationException("Not enough funds");
        acc.setBalance(acc.getBalance() - amount);
        acc.addTransaction(new Transaction(acc, "WITHDRAW -" + amount));

        AccountDto dto = mapper.toDto(acc);
        events.publishEvent(new AccountDomainEvent(accNum, "WITHDRAW", dto));
        return dto;
    }

    @Transactional
    public void transfer(String fromNum, String toNum, int amount) {
        Account from = find(fromNum);
        Account to   = find(toNum);

        int commission = calcCommission(amount, from.getOwner(), to.getOwner());
        int total      = amount + commission;
        if (from.getBalance() < total)
            throw new ValidationException("Not enough funds (with commission)");

        from.setBalance(from.getBalance() - total);
        to.setBalance(  to.getBalance() + amount);

        from.addTransaction(new Transaction(from,
                "TRANSFER -" + amount + " (commission " + commission + ") -> " + toNum));
        to.addTransaction(new Transaction(to,
                "RECEIVE +"  + amount + " <- " + fromNum));

        events.publishEvent(new AccountDomainEvent(fromNum, "TRANSFER_OUT", mapper.toDto(from)));
        events.publishEvent(new AccountDomainEvent(toNum,   "TRANSFER_IN",  mapper.toDto(to)));
    }

    public List<String> getTransactionsByFilter(String accNum, String substring) {
        return find(accNum).getTransactions().stream()
                .map(Transaction::getDescription)
                .filter(d -> d.toUpperCase().contains(substring.toUpperCase()))
                .toList();
    }

    private Account find(String accNum) {
        UUID uuid = UUID.fromString(accNum);
        return accountDao.findByAccountNumber(uuid)
                .orElseThrow(() -> new AccountNotFoundException(accNum));
    }

    private int calcCommission(int amount, User from, User to) {
        if (from.equals(to)) return 0;
        double rate = from.getFriends().contains(to) ? 0.03 : 0.10;
        return (int) Math.round(amount * rate);
    }
}