package ru.otus.home7.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.otus.home7.domain.Genre;

public interface GenreRepository extends JpaRepository<Genre, Long> {
    Genre findByName(String name);
}
