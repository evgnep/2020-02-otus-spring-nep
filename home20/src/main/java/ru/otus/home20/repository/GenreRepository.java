package ru.otus.home20.repository;

import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import reactor.core.publisher.Mono;
import ru.otus.home20.domain.Genre;

public interface GenreRepository extends ReactiveMongoRepository<Genre, Long> {
    Mono<Genre> findByName(String name);
}
