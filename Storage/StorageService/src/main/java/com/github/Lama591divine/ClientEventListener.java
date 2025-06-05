package com.github.Lama591divine;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class ClientEventListener {
    private final ClientEventRepository repo;
    private final ObjectMapper mapper = new ObjectMapper();

    @KafkaListener(topics="client-topic", groupId="storage-service-group")
    public void on(ConsumerRecord<String,String> rec) {
        repo.save(ClientEventEntity.builder()
                .entityId(rec.key())
                .eventType(extract(rec.value()))
                .payload(rec.value())
                .build());
    }

    private String extract(String json) {
        try {
            return mapper.readTree(json).path("eventType").asText("UNKNOWN");
        }
        catch (Exception e) {
            return "UNKNOWN";
        }
    }
}
