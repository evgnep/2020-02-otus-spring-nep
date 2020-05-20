package ru.otus.home7.rest;

import org.springframework.web.bind.annotation.*;
import ru.otus.home7.repository.AuthorRepository;
import ru.otus.home7.rest.dto.AuthorConverters;
import ru.otus.home7.rest.dto.AuthorDto;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping(path = "/rest/author")
public class AuthorRestController {
    private final AuthorRepository repository;

    public AuthorRestController(AuthorRepository repository) {
        this.repository = repository;
    }

    @GetMapping
    public List<AuthorDto> getAll() {
        return repository.findAll().stream().map(AuthorConverters::toDto).collect(Collectors.toList());
    }

    @GetMapping("/{id}")
    public AuthorDto get(@PathVariable long id) {
        return AuthorConverters.toDto(repository.findById(id).orElseThrow());
    }

    @RequestMapping(method = {RequestMethod.POST, RequestMethod.PUT})
    public AuthorDto save(@RequestBody AuthorDto author) {
        return AuthorConverters.toDto(repository.save(AuthorConverters.fromDto(author)));
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable long id) {
        repository.deleteById(id);
    }
}
