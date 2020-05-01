package ru.otus.home7.shell;

import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import ru.otus.home7.dao.Dao;
import ru.otus.home7.domain.Author;

import javax.transaction.Transactional;

@ShellComponent
@Transactional
class AuthorShell {
    private final CrudShellImpl<Author> impl;

    public AuthorShell(Dao<Author> dao) {
        impl = new CrudShellImpl<>(dao);
    }

    @ShellMethod(value = "Чтение всех авторов")
    public String readAllAuthors() {
        return impl.readAll();
    }

    @ShellMethod(value = "Чтение автора по ID")
    public String readAuthorById(int id) {
        return impl.readById(id);
    }

    @ShellMethod(value = "Количество авторов")
    public String authorCount() {
        return impl.count();
    }

    @ShellMethod(value = "Удаление автора")
    public String deleteAuthor(int id) {
        return impl.delete(id);
    }

    @ShellMethod(value = "Создание автора")
    public String createAuthor(String name) {
        return impl.create(Author.builder().name(name).build());
    }

    @ShellMethod(value = "Изменение автора")
    public String modifyAuthor(int id, String name) {
        return impl.update(id, a -> a.setName(name));
    }

}
