package com.github.lama591divine.dto.request;

import com.github.lama591divine.enums.Gender;
import com.github.lama591divine.enums.HairColor;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record CreateUserRequest(
        @NotBlank String login,
        @NotBlank String name,
        @Min(14) Integer age,
        @NotNull Gender gender,
        @NotNull HairColor hairColor
) {}
