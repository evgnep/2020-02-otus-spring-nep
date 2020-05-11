package ru.otus.home13.repository;

import org.springframework.data.repository.CrudRepository;
import ru.otus.home13.domain.Author;

public interface AuthorRepository extends CrudRepository<Author, String> {
    Author findByName(String name);
}
