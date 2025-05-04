package com.github.lama591divine.dto.request;

import jakarta.validation.constraints.NotBlank;

public record RequestTransaction(@NotBlank String id, @NotBlank String typeTransaction) {
}
