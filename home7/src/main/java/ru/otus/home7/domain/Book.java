package ru.otus.home7.domain;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class Book {
    private long id;
    private String name;
    private String description;
    private Author author;
    private Genre genre;
}
