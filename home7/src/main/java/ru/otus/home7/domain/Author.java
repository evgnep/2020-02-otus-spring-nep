package ru.otus.home7.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "author")
public class Author {
    @Id
    @Column(name = "id")
    @GeneratedValue
    private long id;

    @Column(name = "name")
    private String name;
}
