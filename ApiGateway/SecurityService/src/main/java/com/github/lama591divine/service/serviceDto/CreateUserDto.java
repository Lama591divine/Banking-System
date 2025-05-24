package com.github.lama591divine.service.serviceDto;

import com.github.lama591divine.enums.Gender;
import com.github.lama591divine.enums.HairColor;

public record CreateUserDto(
        String login,
        String name,
        Integer age,
        Gender gender,
        HairColor hairColor) {}
