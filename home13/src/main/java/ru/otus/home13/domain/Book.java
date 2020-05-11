package ru.otus.home13.domain;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.ArrayList;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Document("Book")
public class Book {
    @Id
    private String id;

    private String name;
    private String description;

    @EqualsAndHashCode.Exclude
    @Builder.Default
    private List<BookComment> comments = new ArrayList<>();

    @DBRef
    @Builder.Default
    private List<Author> authors = new ArrayList<>();

    private Genre genre;

}
