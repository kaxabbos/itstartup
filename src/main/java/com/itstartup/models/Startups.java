package com.itstartup.models;

import com.itstartup.models.enums.Field;
import com.itstartup.models.enums.Kinds;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@Entity
public class Startups {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Setter(AccessLevel.NONE)
    @Column(nullable = false, updatable = false)
    private Long id;
    private String name;
    private String pub;
    @Column(length = 5000)
    private String description;
    private String date;
    private String poster;
    private String[] screenshots;
    private int year;
    private int count;
    @Enumerated(EnumType.STRING)
    private Field field;
    @Enumerated(EnumType.STRING)
    private Kinds kind;
    @ManyToOne(fetch = FetchType.LAZY)
    private Users owner;
    @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private Investment investment;
    @OneToMany(mappedBy = "book", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<Comments> comments;
    @OneToMany(mappedBy = "startup", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<Carts> carts;

    public Startups(Kinds kind, Users owner, String name, String pub, String description, String date, String poster, String[] screenshots, int year, int price, int count, Field field) {
        this.kind = kind;
        this.owner = owner;
        this.name = name;
        this.pub = pub;
        this.description = description;
        this.date = date;
        this.poster = poster;
        this.screenshots = screenshots;
        this.year = year;
        this.count = count;
        this.field = field;
        this.investment = new Investment(price);
    }

    public void addComment(Comments comment) {
        comments.add(comment);
        comment.setBook(this);
    }
}
