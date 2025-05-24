package com.github.lama591divine.service;

import com.github.lama591divine.service.serviceDto.AccountDto;
import com.github.lama591divine.service.serviceDto.MoneyDto;
import com.github.lama591divine.service.serviceDto.OpenAccountDto;
import com.github.lama591divine.service.serviceDto.TransferDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AccountService {

    private final CoreServiceProxy proxy;

    public List<AccountDto> getAllAccounts() {
        return proxy.getAllAccounts();
    }

    public List<AccountDto> getAccountsOfUser(String l) {
        return proxy.getAccountsOfUser(l);
    }

    public AccountDto getAccount(String num) {
        return proxy.getAccount(num);
    }

    public void openAccount(OpenAccountDto dto) {
        proxy.openAccount(dto);
    }

    public void deposit(MoneyDto dto) {
        proxy.deposit(dto);
    }

    public void withdraw(MoneyDto dto) {
        proxy.withdraw(dto);
    }

    public void transfer(TransferDto dto) {
        proxy.transfer(dto);
    }
}