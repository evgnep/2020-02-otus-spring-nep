package ru.otus.home7.dao;

import ru.otus.home7.domain.Genre;

public interface GenreDao extends Crud<Genre> {
    Genre readByName(String name);
}
