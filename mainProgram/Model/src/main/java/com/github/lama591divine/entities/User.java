package com.github.lama591divine.entities;

import com.github.lama591divine.enums.Gender;
import com.github.lama591divine.enums.HairColor;
import jakarta.persistence.*;
import lombok.*;

import java.util.HashSet;
import java.util.Set;

@Entity
@Getter @Setter @NoArgsConstructor
@Table(name = "users")
public class User {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true, nullable = false)
    private String login;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private Integer age;

    @Enumerated(EnumType.STRING)
    private Gender gender;

    @Enumerated(EnumType.STRING)
    private HairColor hairColor;

    @ManyToMany
    @JoinTable(name = "user_friends", joinColumns        = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "friend_id"))
    private Set<User> friends = new HashSet<>();

    @OneToMany(mappedBy = "owner", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<Account> accounts = new HashSet<>();

    public User(String login, String name, Integer age,
                Gender gender, HairColor hairColor) {
        this.login     = login;
        this.name      = name;
        this.age       = age;
        this.gender    = gender;
        this.hairColor = hairColor;
    }

    public void addFriend(User friend) {
        friends.add(friend);
        friend.friends.add(this);
    }
    public void removeFriend(User friend) {
        friends.remove(friend);
        friend.friends.remove(this);
    }
}

