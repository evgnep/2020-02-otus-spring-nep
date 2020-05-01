package ru.otus.home7.dao;

import ru.otus.home7.domain.Author;

public interface AuthorDao extends Dao<Author> {
    Author readByName(String name);
}
