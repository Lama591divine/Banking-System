package com.github.lama591divine.dto.request;

import jakarta.validation.constraints.NotBlank;

public record CreateAccountDto(@NotBlank String userLogin) {}
