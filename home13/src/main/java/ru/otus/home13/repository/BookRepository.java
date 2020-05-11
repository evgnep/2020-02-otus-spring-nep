package ru.otus.home13.repository;

import org.springframework.data.repository.CrudRepository;
import ru.otus.home13.domain.Book;

public interface BookRepository extends CrudRepository<Book, String> {
}
