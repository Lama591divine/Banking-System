package com.github.lama591divine.dto.response;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;

import java.util.List;

public record AccountDto(
        @NotBlank String accountNumber,
        @Min(0) Integer balance,
        @NotBlank String ownerLogin,
        List<String> transactions
) {}
