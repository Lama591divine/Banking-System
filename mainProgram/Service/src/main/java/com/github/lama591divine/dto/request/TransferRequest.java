package com.github.lama591divine.dto.request;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;

public record TransferRequest(
        @NotBlank String fromId,
        @NotBlank String toId,
        @Min(1) int amount) {}
