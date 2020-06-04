package ru.otus.home20.controller.dto;

import ru.otus.home20.domain.Author;
import ru.otus.home20.domain.Book;
import ru.otus.home20.domain.Genre;
import ru.otus.home7.rest.dto.BookDto;

import java.util.List;

public class BookConverters {
    public static BookDto toDto(Book src) {
        return BookDto.builder()
                .id(src.getId())
                .name(src.getName())
                .description(src.getDescription())
                .author(AuthorConverters.toDto(src.getAuthors().get(0)))
                .genre(GenreConverters.toDto(src.getGenre()))
                .build();
    }

    public static Book fromDto(BookDto src) {
        return Book.builder()
                .id(src.getId())
                .name(src.getName())
                .description(src.getDescription())
                .authors(List.of(Author.builder().id(src.getAuthor().getId()).build()))
                .genre(Genre.builder().id(src.getGenre().getId()).build())
                .build();
    }
}
