package com.github.lama591divine.dto.response;

import com.github.lama591divine.enums.Gender;
import com.github.lama591divine.enums.HairColor;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;

import java.util.Set;

public record UserDto(@NotBlank String login, @NotBlank String name, @Min(14) Integer age, @NotBlank Gender gender,
                      @NotBlank HairColor hairColor, @NotBlank Set<String> friends, @NotBlank Set<String> accounts) {}
