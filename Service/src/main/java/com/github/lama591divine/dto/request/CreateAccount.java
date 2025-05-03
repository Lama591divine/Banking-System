package com.github.lama591divine.dto.request;

import jakarta.validation.constraints.NotBlank;

public record CreateAccount(@NotBlank String userLogin) {}
