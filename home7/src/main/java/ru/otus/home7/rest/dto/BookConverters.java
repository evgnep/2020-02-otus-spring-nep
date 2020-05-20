package ru.otus.home7.rest.dto;

import ru.otus.home7.domain.Author;
import ru.otus.home7.domain.Book;
import ru.otus.home7.domain.Genre;

public class BookConverters {
    public static BookDto toDto(Book src) {
        return BookDto.builder()
                .id(src.getId())
                .name(src.getName())
                .description(src.getDescription())
                .author(AuthorConverters.toDto(src.getAuthor()))
                .genre(GenreConverters.toDto(src.getGenre()))
                .build();
    }

    public static Book fromDto(BookDto src) {
        return Book.builder()
                .id(src.getId())
                .name(src.getName())
                .description(src.getDescription())
                .author(Author.builder().id(src.getAuthor().getId()).build())
                .genre(Genre.builder().id(src.getGenre().getId()).build())
                .build();
    }
}
