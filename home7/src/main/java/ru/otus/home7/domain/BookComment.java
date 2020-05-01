package ru.otus.home7.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Table(name = "book_comment")
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Data
public class BookComment {
    @Id
    @Column(name = "id")
    @GeneratedValue
    long id;

    @Column(name = "author")
    String author;

    @Column(name = "comment")
    String comment;
}
