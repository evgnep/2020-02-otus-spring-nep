package ru.otus.home20.controller;

import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import ru.otus.home20.controller.dto.GenreConverters;
import ru.otus.home20.domain.Genre;
import ru.otus.home20.repository.GenreRepository;
import ru.otus.home20.repository.SaveService;
import ru.otus.home7.rest.dto.GenreDto;

@RestController
@RequestMapping("/rest/genre")
public class GenreController {
    private final GenreRepository repository;
    private final SaveService saveService;

    public GenreController(GenreRepository repository, SaveService saveService) {
        this.repository = repository;
        this.saveService = saveService;
    }

    @GetMapping
    public Flux<GenreDto> getAll() {
        return repository.findAll().map(GenreConverters::toDto);
    }

    @GetMapping("/{id}")
    public Mono<GenreDto> get(@PathVariable long id) {
        return repository.findById(id).map(GenreConverters::toDto);
    }

    @RequestMapping(method = {RequestMethod.POST, RequestMethod.PUT})
    public Mono<GenreDto> save(@RequestBody Mono<GenreDto> genre) {
        return saveService.save(genre.map(GenreConverters::fromDto), Genre::getId, Genre::setId)
                .map(GenreConverters::toDto);
    }

    @DeleteMapping("/{id}")
    public Mono<Void> delete(@PathVariable long id) {
        return repository.deleteById(id);
    }
}
