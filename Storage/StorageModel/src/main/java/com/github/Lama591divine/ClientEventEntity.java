package com.github.Lama591divine;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

import java.time.Instant;

@Entity
@Table(name="client_events")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ClientEventEntity {

    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    Long id;

    @Column(nullable=false,length=64)
    String entityId;

    @Column(nullable=false,length=32)
    String eventType;

    @Column(columnDefinition="jsonb",nullable=false)
    String payload;

    @CreationTimestamp
    Instant createdAt;
}