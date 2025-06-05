package com.github.lama591divine.dto.kafkadto;

import com.github.lama591divine.dto.response.UserDto;

public record UserDomainEvent(String login, String eventType, UserDto snapshot) {}
