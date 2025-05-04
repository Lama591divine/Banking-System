package com.github.lama591divine.exception;

import lombok.Getter;

@Getter
public class AccountNotFoundException extends RuntimeException {
    private final String id;

    public AccountNotFoundException(String id) {
        super("Account '" + id + "' not found");
        this.id = id;
    }
}
