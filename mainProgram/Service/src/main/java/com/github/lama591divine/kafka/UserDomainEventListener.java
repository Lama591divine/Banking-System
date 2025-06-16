package com.github.lama591divine.kafka;

import com.github.lama591divine.dto.kafkadto.UserDomainEvent;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.event.TransactionPhase;
import org.springframework.transaction.event.TransactionalEventListener;

@Component
@RequiredArgsConstructor
public class UserDomainEventListener {

    private final EventPublisher publisher;

    @TransactionalEventListener(phase = TransactionPhase.AFTER_COMMIT)
    public void on(UserDomainEvent ev) {
        publisher.sendClientEvent(ev.login(), ev);
    }
}
