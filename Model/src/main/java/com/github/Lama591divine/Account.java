package com.github.Lama591divine;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

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

    @ManyToOne(fetch = FetchType.LAZY)
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
