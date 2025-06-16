// events/EventPublisher.java
package com.github.lama591divine.kafka;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@Component @RequiredArgsConstructor @Slf4j
public class EventPublisher {

    private final KafkaTemplate<String,String> kafka;
    private final ObjectMapper mapper = new ObjectMapper();

    public void sendAccountEvent(String key, Object payload) {
        send("account-topic", key, payload);
    }
    public void sendClientEvent(String key, Object payload) {
        send("client-topic", key, payload);
    }

    private void send(String topic, String key, Object obj) {
        try {
            kafka.send(topic, key, mapper.writeValueAsString(obj));
        } catch (JsonProcessingException e) {
            log.error("Не удалось сериализовать событие {}", obj, e);
        }
    }
}
