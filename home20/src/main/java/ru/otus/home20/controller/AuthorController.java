package ru.otus.home20.controller;

import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import ru.otus.home20.controller.dto.AuthorConverters;
import ru.otus.home20.domain.Author;
import ru.otus.home20.repository.AuthorRepository;
import ru.otus.home20.repository.SaveService;
import ru.otus.home7.rest.dto.AuthorDto;

@RestController
@RequestMapping("/rest/author")
public class AuthorController {
    private final AuthorRepository repository;
    private final SaveService saveService;

    public AuthorController(AuthorRepository repository, SaveService saveService) {
        this.repository = repository;
        this.saveService = saveService;
    }

    @GetMapping
    public Flux<AuthorDto> getAll() {
        return repository.findAll().map(AuthorConverters::toDto);
    }

    @GetMapping("/{id}")
    public Mono<AuthorDto> get(@PathVariable long id) {
        return repository.findById(id).map(AuthorConverters::toDto);
    }

    @RequestMapping(method = {RequestMethod.POST, RequestMethod.PUT})
    public Mono<AuthorDto> save(@RequestBody Mono<AuthorDto> author) {
        return saveService.save(author.map(AuthorConverters::fromDto), Author::getId, Author::setId)
                .map(AuthorConverters::toDto);
    }

    @DeleteMapping("/{id}")
    public Mono<Void> delete(@PathVariable long id) {
        return repository.deleteById(id);
    }
}
