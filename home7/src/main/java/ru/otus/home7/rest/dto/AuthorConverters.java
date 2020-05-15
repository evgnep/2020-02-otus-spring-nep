package ru.otus.home7.rest.dto;

import ru.otus.home7.domain.Author;

public class AuthorConverters {
    public static AuthorDto toDto(Author src) {
        return AuthorDto.builder().id(src.getId()).name(src.getName()).build();
    }

    public static Author fromDto(AuthorDto src) {
        return Author.builder().id(src.getId()).name(src.getName()).build();
    }
}
