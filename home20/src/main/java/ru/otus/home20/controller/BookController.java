package ru.otus.home20.controller;

import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import ru.otus.home20.controller.dto.BookConverters;
import ru.otus.home20.domain.Book;
import ru.otus.home20.repository.AuthorRepository;
import ru.otus.home20.repository.BookRepository;
import ru.otus.home20.repository.GenreRepository;
import ru.otus.home20.repository.SaveService;
import ru.otus.home7.rest.dto.BookDto;

import java.util.List;

@RestController
@RequestMapping("/rest/book")
public class BookController {
    private final BookRepository repository;
    private final GenreRepository genreRepository;
    private final AuthorRepository authorRepository;
    private final SaveService saveService;

    public BookController(BookRepository repository, GenreRepository genreRepository, AuthorRepository authorRepository, SaveService saveService) {
        this.repository = repository;
        this.genreRepository = genreRepository;
        this.authorRepository = authorRepository;
        this.saveService = saveService;
    }

    @GetMapping
    public Flux<BookDto> getAll() {
        return repository.findAll().map(BookConverters::toDto);
    }

    @GetMapping("/{id}")
    public Mono<BookDto> get(@PathVariable long id) {
        return repository.findById(id).map(BookConverters::toDto);
    }

    @RequestMapping(method = {RequestMethod.POST, RequestMethod.PUT})
    public Mono<BookDto> save(@RequestBody Mono<BookDto> bookDto) {
        return saveService.save(bookDto.map(BookConverters::fromDto), Book::getId, Book::setId)
                .zipWhen(book1 -> genreRepository.findById(book1.getGenre().getId()), Book::setGenre)
                .zipWhen(book1 -> authorRepository.findById(book1.getAuthors().get(0).getId()),
                        (book1, author) -> book1.setAuthors(List.of(author))
                )
                .map(BookConverters::toDto);
    }

    @DeleteMapping("/{id}")
    public Mono<Void> delete(@PathVariable long id) {
        return repository.deleteById(id);
    }
}
