package ru.otus.home7.shell;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;

import java.util.function.Consumer;
import java.util.stream.Collectors;

/**
 * Вспомогательный класс для реализации crud-подобных комбинаций методов
 */
class CrudShellImpl<T> {
    private final JpaRepository<T, Long> repository;

    public CrudShellImpl(JpaRepository<T, Long> repository) {
        this.repository = repository;
    }

    public CrudRepository<T, Long> getRepository() {
        return repository;
    }

    public String readAll() {
        return repository.findAll().stream().map(Object::toString).collect(Collectors.joining("\n"));
    }

    public String readById(long id) {
        return repository.findById(id).toString();
    }

    public String count() {
        return Long.toString(repository.count());
    }

    public String delete(long id) {
        repository.deleteById(id);
        return "deleted";
    }

    public String create(T elem) {
        repository.save(elem);
        return "created: " + elem;
    }

    public String update(long id, Consumer<T> updater) {
        var elem = repository.findById(id).orElseThrow();
        updater.accept(elem);
        repository.flush();
        return "updated: " + elem;
    }
}
