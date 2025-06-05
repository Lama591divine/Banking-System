package com.github.lama591divine.dto.kafkadto;

import com.github.lama591divine.dto.response.AccountDto;

public record AccountDomainEvent(String accountId, String eventType, AccountDto snapshot) {}

