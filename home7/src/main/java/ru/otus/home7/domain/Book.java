package ru.otus.home7.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "book")
public class Book {
    @Id
    @Column(name = "id")
    private long id;
    @Column(name = "name")
    private String name;
    @Column(name = "description")
    private String description;
    @ManyToOne(cascade = CascadeType.ALL, optional = false)
    @JoinColumn(name = "author")
    private Author author;
    @ManyToOne(cascade = CascadeType.ALL, optional = false)
    @JoinColumn(name = "genre")
    private Genre genre;
}
