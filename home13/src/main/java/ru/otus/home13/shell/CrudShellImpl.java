package ru.otus.home13.shell;

import org.springframework.data.repository.CrudRepository;

import java.util.function.Consumer;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

/**
 * Вспомогательный класс для реализации crud-подобных комбинаций методов
 */
class CrudShellImpl<T> {
    private final CrudRepository<T, String> repository;

    public CrudShellImpl(CrudRepository<T, String> repository) {
        this.repository = repository;
    }

    public CrudRepository<T, String> getRepository() {
        return repository;
    }

    public String readAll() {
        return StreamSupport.stream(repository.findAll().spliterator(), false).map(Object::toString).collect(Collectors.joining("\n"));
    }

    public String readById(String id) {
        return repository.findById(id).toString();
    }

    public String count() {
        return Long.toString(repository.count());
    }

    public String delete(String id) {
        repository.deleteById(id);
        return "deleted";
    }

    public String create(T elem) {
        repository.save(elem);
        return "created: " + elem;
    }

    public String update(String id, Consumer<T> updater) {
        var elem = repository.findById(id).orElseThrow();
        updater.accept(elem);
        repository.save(elem);
        return "updated: " + elem;
    }
}
