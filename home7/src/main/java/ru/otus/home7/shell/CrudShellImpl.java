package ru.otus.home7.shell;

import ru.otus.home7.dao.Dao;

import java.util.function.Consumer;
import java.util.stream.Collectors;

/**
 * Вспомогательный класс для реализации crud-подобных комбинаций методов
 */
class CrudShellImpl<T> {
    private final Dao<T> dao;

    public CrudShellImpl(Dao<T> dao) {
        this.dao = dao;
    }

    public Dao<T> getDao() {
        return dao;
    }

    public String readAll() {
        return dao.readAll().stream().map(Object::toString).collect(Collectors.joining("\n"));
    }

    public String readById(int id) {
        return dao.readById(id).toString();
    }

    public String count() {
        return Long.toString(dao.count());
    }

    public String delete(int id) {
        dao.delete(id);
        return "deleted";
    }

    public String create(T elem) {
        dao.save(elem);
        return "created: " + elem;
    }

    public String update(long id, Consumer<T> updater) {
        var elem = dao.readById(id);
        updater.accept(elem);
        dao.flush();
        return "updated: " + elem;
    }
}
