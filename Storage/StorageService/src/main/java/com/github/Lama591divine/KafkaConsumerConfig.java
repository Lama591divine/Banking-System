package com.github.Lama591divine;

import com.fasterxml.jackson.databind.deser.std.StringDeserializer;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.annotation.EnableKafka;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.core.DefaultKafkaConsumerFactory;

import java.util.Map;

@Configuration
@EnableKafka
public class KafkaConsumerConfig {

    @Value("${spring.kafka.bootstrap-servers}") String bootstrap;

    @Bean
    public ConcurrentKafkaListenerContainerFactory<String,String> kafkaFactory() {
        Map<String,Object> cfg = Map.of(
                ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrap,
                ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class,
                ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class,
                ConsumerConfig.GROUP_ID_CONFIG, "storage-service-group",
                ConsumerConfig.AUTO_OFFSET_RESET_CONFIG, "earliest");
        ConcurrentKafkaListenerContainerFactory<String,String> f =
                new ConcurrentKafkaListenerContainerFactory<>();
        f.setConsumerFactory(new DefaultKafkaConsumerFactory<>(cfg));
        return f;
    }
}

