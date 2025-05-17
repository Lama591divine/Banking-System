package com.github.Lama591divine.service.serviceDto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;

import java.util.Set;

public record UserDto(@NotBlank String login, @NotBlank String name, @Min(14) Integer age, @NotBlank String gender,
                      @NotBlank String hairColor, @NotBlank Set<String> friends, @NotBlank Set<String> accounts) {}
