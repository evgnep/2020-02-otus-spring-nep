package ru.otus.home7.shell;

import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import org.springframework.shell.standard.ShellOption;
import ru.otus.home7.dao.AuthorDao;
import ru.otus.home7.dao.Crud;
import ru.otus.home7.dao.GenreDao;
import ru.otus.home7.domain.Book;

@ShellComponent
public class BookShell extends AbstractCrudShell<Book> {
    private final AuthorDao authorDao;
    private final GenreDao genreDao;

    public BookShell(Crud<Book> crud, AuthorDao authorDao, GenreDao genreDao) {
        super(crud);
        this.authorDao = authorDao;
        this.genreDao = genreDao;
    }

    @ShellMethod(value = "Чтение всех книг")
    public String readAllBooks() {
        return readAll();
    }

    @ShellMethod(value = "Чтение книги по ID")
    public String readBookById(int id) {
        return readById(id);
    }

    @ShellMethod(value = "Количество книг")
    public String bookCount() {
        return count();
    }

    @ShellMethod(value = "Удаление книги")
    public String deleteBook(int id) {
        return delete(id);
    }

    private Book updateBook(Book book, String name, String description, String authorName, String genreName) {
        if (name != null)
            book.setName(name);
        if (description != null)
            book.setDescription(description);
        if (authorName != null)
            book.setAuthor(authorDao.readByName(authorName));
        if (genreName != null)
            book.setGenre(genreDao.readByName(genreName));
        return book;
    }

    @ShellMethod(value = "Создание книги")
    public String createBook(int id, String name, String description, String authorName, String genreName) {
        return create(updateBook(Book.builder().id(id).build(), name, description, authorName, genreName));
    }

    @ShellMethod(value = "Изменение книги. Если поле не изменяется - не указывайте его")
    public String modifyBook(int id,
                             @ShellOption(defaultValue = ShellOption.NULL) String name,
                             @ShellOption(defaultValue = ShellOption.NULL) String description,
                             @ShellOption(defaultValue = ShellOption.NULL) String authorName,
                             @ShellOption(defaultValue = ShellOption.NULL) String genreName) {
        return update(id, b -> updateBook(b, name, description, authorName, genreName));
    }

}
