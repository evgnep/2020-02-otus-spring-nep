package ru.otus.home20.controller.dto;

import ru.otus.home20.domain.Genre;
import ru.otus.home7.rest.dto.GenreDto;

public class GenreConverters {
    public static GenreDto toDto(Genre src) {
        return GenreDto.builder().id(src.getId()).name(src.getName()).build();
    }

    public static Genre fromDto(GenreDto src) {
        return Genre.builder().id(src.getId()).name(src.getName()).build();
    }
}
