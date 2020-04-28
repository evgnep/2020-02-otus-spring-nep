package ru.otus.home7.dao;

import java.util.List;

public interface Crud<T> {
    long count();

    void create(T elem);

    void delete(long id);

    T readById(long id);

    List<T> readAll();

    void flush();
}
