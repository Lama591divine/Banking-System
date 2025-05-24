package com.github.lama591divine.mapper;

import com.github.lama591divine.dao.AccountDao;
import com.github.lama591divine.dao.UserDao;
import com.github.lama591divine.dto.response.AccountDto;
import com.github.lama591divine.dto.response.UserDto;
import com.github.lama591divine.entities.Account;
import com.github.lama591divine.entities.Transaction;
import com.github.lama591divine.entities.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Component @RequiredArgsConstructor
public class DtoMapper {

    private final AccountDao accountDao;
    private final UserDao    userDao;

    public AccountDto toDto(Account account) {
        List<String> tx = account.getTransactions()
                .stream()
                .map(Transaction::getDescription)
                .toList();

        return new AccountDto(
                account.getAccountNumber().toString(),
                account.getBalance(),
                account.getOwner().getLogin(),
                tx
        );
    }

    public UserDto toDto(User user) {
        Set<String> friends  = user.getFriends()
                .stream()
                .map(User::getLogin)
                .collect(Collectors.toSet());

        Set<String> accounts = user.getAccounts()
                .stream()
                .map(acc -> acc.getAccountNumber().toString())
                .collect(Collectors.toSet());

        return new UserDto(
                user.getLogin(), user.getName(), user.getAge(),
                user.getGender(), user.getHairColor(),
                friends, accounts
        );
    }
}

