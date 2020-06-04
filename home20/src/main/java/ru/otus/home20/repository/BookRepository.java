package ru.otus.home20.repository;

import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import ru.otus.home20.domain.Book;

public interface BookRepository extends ReactiveMongoRepository<Book, Long> {
}
