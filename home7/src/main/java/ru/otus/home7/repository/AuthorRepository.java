package ru.otus.home7.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.otus.home7.domain.Author;

public interface AuthorRepository extends JpaRepository<Author, Long> {
    Author findByName(String name);
}
