package ru.otus.home7.shell;

import ru.otus.home7.dao.Dao;

import java.util.function.Consumer;
import java.util.stream.Collectors;

abstract class AbstractCrudShell<T> {
    protected final Dao<T> crud;

    public AbstractCrudShell(Dao<T> crud) {
        this.crud = crud;
    }

    public String readAll() {
        return crud.readAll().stream().map(Object::toString).collect(Collectors.joining("\n"));
    }

    public String readById(int id) {
        return crud.readById(id).toString();
    }

    public String count() {
        return Long.toString(crud.count());
    }

    public String delete(int id) {
        crud.delete(id);
        return "deleted";
    }

    public String create(T elem) {
        crud.save(elem);
        return "created: " + elem;
    }

    public String update(long id, Consumer<T> updater) {
        var elem = crud.readById(id);
        updater.accept(elem);
        crud.flush();
        return "updated: " + elem;
    }
}
