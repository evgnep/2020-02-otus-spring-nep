package ru.otus.home7.rest;

import org.springframework.web.bind.annotation.*;
import ru.otus.home7.repository.BookRepository;
import ru.otus.home7.rest.dto.BookConverters;
import ru.otus.home7.rest.dto.BookDto;

import javax.transaction.Transactional;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping(path = "/rest/book")
public class BookRestController {
    private final BookRepository repository;

    public BookRestController(BookRepository repository) {
        this.repository = repository;
    }

    @GetMapping
    public List<BookDto> getAll() {
        return repository.findAll().stream().map(BookConverters::toDto).collect(Collectors.toList());
    }

    @GetMapping("/{id}")
    public BookDto get(@PathVariable long id) {
        return BookConverters.toDto(repository.findById(id).orElseThrow());
    }

    @RequestMapping(method = {RequestMethod.POST, RequestMethod.PUT})
    @Transactional
    public BookDto save(@RequestBody BookDto book) {
        var id = repository.save(BookConverters.fromDto(book)).getId();
        return BookConverters.toDto(repository.findById(id).orElseThrow());
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable long id) {
        repository.deleteById(id);
    }
}
