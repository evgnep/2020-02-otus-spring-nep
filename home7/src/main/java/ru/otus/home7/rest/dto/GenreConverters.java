package ru.otus.home7.rest.dto;

import ru.otus.home7.domain.Genre;

public class GenreConverters {
    public static GenreDto toDto(Genre src) {
        return GenreDto.builder().id(src.getId()).name(src.getName()).build();
    }

    public static Genre fromDto(GenreDto src) {
        return Genre.builder().id(src.getId()).name(src.getName()).build();
    }
}
