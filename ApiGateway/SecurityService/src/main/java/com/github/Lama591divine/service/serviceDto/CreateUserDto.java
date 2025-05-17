package com.github.Lama591divine.service.serviceDto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;

public record CreateUserDto(@NotBlank String login, @NotBlank String password, @NotBlank String name, @Min(14) Integer age, @NotBlank String gender,
                            @NotBlank String hairColor) {
}
