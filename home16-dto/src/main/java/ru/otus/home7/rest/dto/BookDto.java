package ru.otus.home7.rest.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
public class BookDto {
    private long id;
    private String name;
    private String description;
    private AuthorDto author;
    private GenreDto genre;
}
