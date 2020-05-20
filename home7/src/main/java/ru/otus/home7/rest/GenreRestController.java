package ru.otus.home7.rest;

import org.springframework.web.bind.annotation.*;
import ru.otus.home7.repository.GenreRepository;
import ru.otus.home7.rest.dto.GenreConverters;
import ru.otus.home7.rest.dto.GenreDto;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping(path = "/rest/genre")
public class GenreRestController {
    private final GenreRepository repository;

    public GenreRestController(GenreRepository repository) {
        this.repository = repository;
    }

    @GetMapping
    public List<GenreDto> getAll() {
        return repository.findAll().stream().map(GenreConverters::toDto).collect(Collectors.toList());
    }

    @GetMapping("/{id}")
    public GenreDto get(@PathVariable long id) {
        return GenreConverters.toDto(repository.findById(id).orElseThrow());
    }

    @RequestMapping(method = {RequestMethod.POST, RequestMethod.PUT})
    public GenreDto save(@RequestBody GenreDto genre) {
        return GenreConverters.toDto(repository.save(GenreConverters.fromDto(genre)));
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable long id) {
        repository.deleteById(id);
    }
}
