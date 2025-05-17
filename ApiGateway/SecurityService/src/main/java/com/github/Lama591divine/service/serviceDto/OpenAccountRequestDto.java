package com.github.Lama591divine.service.serviceDto;

import jakarta.validation.constraints.NotBlank;

public record OpenAccountRequestDto(@NotBlank String userLogin, @NotBlank String currency) {}