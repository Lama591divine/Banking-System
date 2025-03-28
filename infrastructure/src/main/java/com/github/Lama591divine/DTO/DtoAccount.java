package com.github.Lama591divine.DTO;

import java.util.ArrayList;

/**
 * The {@code DtoAccount} class is a Data Transfer Object (DTO) that represents an account
 * in the banking system. It is used to transfer account data between different application layers.
 */
public record DtoAccount(String id, int balance, ArrayList<String> transactions, String owner) {
}
