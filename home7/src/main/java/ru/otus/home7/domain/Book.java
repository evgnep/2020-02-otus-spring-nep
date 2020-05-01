package ru.otus.home7.domain;

import lombok.*;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "book")
public class Book {
    @OneToMany(cascade = CascadeType.PERSIST, orphanRemoval = true)
    @JoinColumn(name = "book", nullable = false)
    @Setter(AccessLevel.NONE)
    @EqualsAndHashCode.Exclude
    private final List<BookComment> comments = new ArrayList<>();

    @Column(name = "name")
    private String name;

    @Column(name = "description")
    private String description;

    @ManyToOne(optional = false)
    @JoinColumn(name = "author")
    private Author author;

    @ManyToOne(optional = false)
    @JoinColumn(name = "genre")
    private Genre genre;
    @Id
    @Column(name = "id")
    @GeneratedValue
    private long id;
}
