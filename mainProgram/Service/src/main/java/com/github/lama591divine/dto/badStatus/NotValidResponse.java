package com.github.lama591divine.dto.badStatus;

import jakarta.validation.constraints.NotBlank;

public record NotValidResponse(@NotBlank String message) {}
