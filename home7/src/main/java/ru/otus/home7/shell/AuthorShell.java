package ru.otus.home7.shell;

import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import ru.otus.home7.dao.Dao;
import ru.otus.home7.domain.Author;

import javax.transaction.Transactional;

@ShellComponent
@Transactional
public class AuthorShell extends AbstractCrudShell<Author> {
    public AuthorShell(Dao<Author> crud) {
        super(crud);
    }

    @ShellMethod(value = "Чтение всех авторов")
    public String readAllAuthors() {
        return readAll();
    }

    @ShellMethod(value = "Чтение автора по ID")
    public String readAuthorById(int id) {
        return readById(id);
    }

    @ShellMethod(value = "Количество авторов")
    public String authorCount() {
        return count();
    }

    @ShellMethod(value = "Удаление автора")
    public String deleteAuthor(int id) {
        return delete(id);
    }

    @ShellMethod(value = "Создание автора")
    public String createAuthor(int id, String name) {
        return create(Author.builder().id(id).name(name).build());
    }

    @ShellMethod(value = "Изменение автора")
    public String modifyAuthor(int id, String name) {
        return update(id, a -> a.setName(name));
    }

}
