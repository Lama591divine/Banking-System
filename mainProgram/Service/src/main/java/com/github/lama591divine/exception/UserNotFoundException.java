package com.github.lama591divine.exception;

import lombok.Getter;

@Getter
public class UserNotFoundException extends RuntimeException {
    private final String login;

    public UserNotFoundException(String login) {
        super("User '" + login + "' not found");
        this.login = login;
    }
}
