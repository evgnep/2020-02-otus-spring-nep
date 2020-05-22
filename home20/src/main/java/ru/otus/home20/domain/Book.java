package ru.otus.home20.domain;

import lombok.*;
import lombok.experimental.Accessors;
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
@Accessors(chain = true)
public class Book {
    @Id
    private long id;

    private String name;
    private String description;

    @EqualsAndHashCode.Exclude
    @Builder.Default
    private List<BookComment> comments = new ArrayList<>();

    @DBRef
    @Builder.Default
    private List<Author> authors = new ArrayList<>();

    @DBRef
    private Genre genre;

}
