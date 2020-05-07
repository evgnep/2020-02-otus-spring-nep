package ru.otus.home13.shell;

import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import org.springframework.transaction.annotation.Transactional;
import ru.otus.home13.domain.Author;
import ru.otus.home13.repository.AuthorRepository;

@ShellComponent
@Transactional
class AuthorShell {
    private final CrudShellImpl<Author> impl;

    public AuthorShell(AuthorRepository repository) {
        impl = new CrudShellImpl<>(repository);
    }

    @ShellMethod(value = "Чтение всех авторов")
    public String readAllAuthors() {
        return impl.readAll();
    }

    @ShellMethod(value = "Чтение автора по ID")
    public String readAuthorById(String id) {
        return impl.readById(id);
    }

    @ShellMethod(value = "Количество авторов")
    public String authorCount() {
        return impl.count();
    }

    @ShellMethod(value = "Удаление автора")
    public String deleteAuthor(String id) {
        return impl.delete(id);
    }

    @ShellMethod(value = "Создание автора")
    public String createAuthor(String name) {
        return impl.create(Author.builder().name(name).build());
    }

    @ShellMethod(value = "Изменение автора")
    public String modifyAuthor(String id, String name) {
        return impl.update(id, a -> a.setName(name));
    }

}
