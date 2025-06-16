package com.github.lama591divine.kafka;

import com.fasterxml.jackson.databind.ser.std.StringSerializer;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.annotation.EnableKafka;
import org.springframework.kafka.core.DefaultKafkaProducerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.core.ProducerFactory;

import java.util.Map;

@Configuration
@EnableKafka
public class KafkaProducerConfig {

    @Value("${spring.kafka.bootstrap-servers}")
    private String bootstrap;

    @Bean
    public ProducerFactory<String,String> producerFactory() {
        Map<String,Object> cfg = Map.of(
                ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrap,
                ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class,
                ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG,StringSerializer.class,
                ProducerConfig.ACKS_CONFIG, "all",
                ProducerConfig.ENABLE_IDEMPOTENCE_CONFIG, true
        );
        return new DefaultKafkaProducerFactory<>(cfg);
    }

    @Bean
    public KafkaTemplate<String,String> kafkaTemplate(ProducerFactory<String,String> pf) {
        return new KafkaTemplate<>(pf);
    }
}

