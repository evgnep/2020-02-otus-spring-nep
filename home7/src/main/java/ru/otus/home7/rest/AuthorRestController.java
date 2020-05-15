package ru.otus.home7.rest;

import org.springframework.web.bind.annotation.*;
import ru.otus.home7.repository.AuthorRepository;
import ru.otus.home7.rest.dto.AuthorConverters;
import ru.otus.home7.rest.dto.AuthorDto;

import java.util.List;
import java.util.stream.Collectors;

@RestController
public class AuthorRestController {
    private final AuthorRepository repository;

    public AuthorRestController(AuthorRepository repository) {
        this.repository = repository;
    }

    @GetMapping("/rest/author")
    public List<AuthorDto> getAll() {
        return repository.findAll().stream().map(AuthorConverters::toDto).collect(Collectors.toList());
    }

    @RequestMapping(value = "/rest/author", method = {RequestMethod.POST, RequestMethod.PUT})
    public AuthorDto save(AuthorDto author) {
        return AuthorConverters.toDto(repository.save(AuthorConverters.fromDto(author)));
    }

    @GetMapping("/rest/author/{id}")
    public AuthorDto get(@PathVariable long id) {
        return AuthorConverters.toDto(repository.findById(id).orElseThrow());
    }

    @DeleteMapping("/rest/author/{id}")
    public void delete(@PathVariable long id) {
        repository.deleteById(id);
    }
}
