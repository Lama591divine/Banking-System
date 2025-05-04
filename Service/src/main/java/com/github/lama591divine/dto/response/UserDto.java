package com.github.lama591divine.dto.response;

import com.github.lama591divine.enums.Gender;
import com.github.lama591divine.enums.HairColor;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.util.Set;

public record UserDto(@NotBlank String login, @NotBlank String name, @Min(14) Integer age, @NotNull Gender gender,
                      @NotNull HairColor hairColor, @NotBlank Set<String> friends, @NotBlank Set<String> accounts) {}
