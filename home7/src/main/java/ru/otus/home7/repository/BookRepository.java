package ru.otus.home7.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.otus.home7.domain.Book;

public interface BookRepository extends JpaRepository<Book, Long> {
}
