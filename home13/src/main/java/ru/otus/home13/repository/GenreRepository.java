package ru.otus.home13.repository;

import org.springframework.data.repository.CrudRepository;
import ru.otus.home13.domain.Genre;

public interface GenreRepository extends CrudRepository<Genre, String> {
    Genre findByName(String name);
}
