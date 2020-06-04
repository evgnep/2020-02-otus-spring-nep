package ru.otus.home20.repository;

import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import reactor.core.publisher.Mono;
import ru.otus.home20.domain.Author;

public interface AuthorRepository extends ReactiveMongoRepository<Author, Long> {
    Mono<Author> findByName(String name);
}
