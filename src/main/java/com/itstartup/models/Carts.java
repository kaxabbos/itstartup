package com.itstartup.models;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Getter
@Setter
@NoArgsConstructor
@Entity
public class Carts {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Setter(AccessLevel.NONE)
    @Column(nullable = false, updatable = false)
    private Long id;
    private int count;
    @ManyToOne(fetch = FetchType.LAZY)
    private Startups startup;
    @ManyToOne(fetch = FetchType.LAZY)
    private Users owner;

    public Carts(int count, Startups startup) {
        this.count = count;
        this.startup = startup;
    }
}
