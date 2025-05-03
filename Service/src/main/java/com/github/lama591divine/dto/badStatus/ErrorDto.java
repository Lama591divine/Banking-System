package com.github.lama591divine.dto.badStatus;

import jakarta.validation.constraints.NotBlank;

public record ErrorDto(@NotBlank String code, @NotBlank String message) {}
