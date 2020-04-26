package ru.otus.home7.shell;

import ru.otus.home7.dao.Crud;

import java.util.stream.Collectors;

public abstract class AbstractCrudShell<T> {
    protected final Crud<T> crud;

    public AbstractCrudShell(Crud<T> crud) {
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
        crud.create(elem);
        return "created: " + elem;
    }

    public String update(T elem) {
        crud.update(elem);
        return "updated: " + elem;
    }
}
