package ru.otus.home7.dao;

import ru.otus.home7.domain.Genre;

public interface GenreDao extends Dao<Genre> {
    Genre readByName(String name);
}
