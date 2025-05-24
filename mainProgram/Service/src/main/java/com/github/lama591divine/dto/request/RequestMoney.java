package com.github.lama591divine.dto.request;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;

public record RequestMoney(@NotBlank String id, @Min(0) Integer amount) {
}
