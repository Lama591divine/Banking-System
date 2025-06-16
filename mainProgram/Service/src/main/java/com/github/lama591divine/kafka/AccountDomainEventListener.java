package com.github.lama591divine.kafka;

import com.github.lama591divine.dto.kafkadto.AccountDomainEvent;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.event.TransactionPhase;
import org.springframework.transaction.event.TransactionalEventListener;

@Component
@RequiredArgsConstructor
public class AccountDomainEventListener {

    private final EventPublisher publisher;

    @TransactionalEventListener(phase = TransactionPhase.AFTER_COMMIT)
    public void on(AccountDomainEvent ev) {
        publisher.sendAccountEvent(ev.accountId(), ev);
    }
}
