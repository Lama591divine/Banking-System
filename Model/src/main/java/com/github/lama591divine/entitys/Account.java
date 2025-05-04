package com.github.lama591divine.entitys;

import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "accounts")
public class Account {

    @Id
    @Column(name = "id")
    private String id;

    @Column(name = "balance", nullable = false)
    private Integer balance;

    @ManyToOne
    @JoinColumn(name = "owner", nullable = false)
    private User owner;

    @ElementCollection
    @CollectionTable(
            name = "transactions",
            joinColumns = @JoinColumn(name = "id")
    )
    @Column(name = "transaction")
    private List<String> transactions;
}
