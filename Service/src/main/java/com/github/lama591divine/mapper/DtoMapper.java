package com.github.lama591divine.mapper;

import com.github.lama591divine.dao.AccountDao;
import com.github.lama591divine.dao.UserDao;
import com.github.lama591divine.dto.response.AccountDto;
import com.github.lama591divine.dto.response.UserDto;
import com.github.lama591divine.entitys.Account;
import com.github.lama591divine.entitys.User;
import com.github.lama591divine.exception.UserNotFoundException;
import org.springframework.stereotype.Component;

import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

@Component
public class DtoMapper {
    private final AccountDao accountDao;
    private final UserDao userDao;

    public DtoMapper(AccountDao accountDao, UserDao userDao) {
        this.accountDao = accountDao;
        this.userDao = userDao;
    }

    public AccountDto toDto(Account account) {
        return new AccountDto(account.getId(), account.getBalance(), account.getOwner().getLogin(), account.getTransactions());
    }

    public Account toEntity(AccountDto accountDto) {
        User user = userDao.findById(accountDto.ownerLogin()).orElseThrow(() -> new UserNotFoundException(accountDto.ownerLogin()));
        return new Account(accountDto.id(), accountDto.balance(), user, accountDto.transactions());
    }

    public UserDto toDto(User user) {
        Set<User> users = user.getFriends();
        Set<Account> accounts = user.getAccounts();
        Set<String> usersLogins = users.stream().map(User::getLogin).collect(Collectors.toSet());
        Set<String> accountsLogins = accounts.stream().map(Account::getId).collect(Collectors.toSet());
        return new UserDto(user.getLogin(), user.getName(), user.getAge(), user.getGender(), user.getHairColor(), usersLogins, accountsLogins);
    }

    public User toEntity(UserDto userDto) {
        Set<User> friendsEntity = new HashSet<>(userDao.findAllById(userDto.friends()));
        Set<Account> accountsEntity = new HashSet<>(accountDao.findAllById(userDto.accounts()));
        return new User(userDto.login(), userDto.name(), userDto.age(), userDto.gender(), userDto.hairColor(), friendsEntity, accountsEntity);
    }
}
