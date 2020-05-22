package ru.otus.home13.shell;

import org.springframework.shell.Availability;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import org.springframework.shell.standard.ShellOption;
import org.springframework.transaction.annotation.Transactional;
import ru.otus.home13.domain.Book;
import ru.otus.home13.domain.BookComment;
import ru.otus.home13.repository.AuthorRepository;
import ru.otus.home13.repository.BookRepository;
import ru.otus.home13.repository.GenreRepository;

@ShellComponent
@Transactional
class BookShell {
    private final CrudShellImpl<Book> impl;
    private final AuthorRepository authorRepository;
    private final GenreRepository genreRepository;
    private String commentAuthor;

    public BookShell(BookRepository bookRepository, AuthorRepository authorRepository, GenreRepository genreRepository) {
        impl = new CrudShellImpl<>(bookRepository);
        this.authorRepository = authorRepository;
        this.genreRepository = genreRepository;
    }

    @ShellMethod(value = "Как ваше имя?")
    public String myName(String name) {
        commentAuthor = name;
        return "Hello, " + name;
    }

    @ShellMethod(value = "Чтение всех книг")
    public String readAllBooks() {
        return impl.readAll();
    }

    @ShellMethod(value = "Чтение книги по ID")
    public String readBookById(String id) {
        return impl.readById(id);
    }

    @ShellMethod(value = "Количество книг")
    public String bookCount() {
        return impl.count();
    }

    @ShellMethod(value = "Удаление книги")
    public String deleteBook(String id) {
        return impl.delete(id);
    }

    private Book updateBook(Book book, String name, String description, String authorName, String genreName) {
        if (name != null)
            book.setName(name);
        if (description != null)
            book.setDescription(description);
        if (authorName != null) {
            book.getAuthors().clear();
            book.getAuthors().add(authorRepository.findByName(authorName));
        }
        if (genreName != null)
            book.setGenre(genreRepository.findByName(genreName));
        return book;
    }

    @ShellMethod(value = "Создание книги")
    public String createBook(String name, String description, String authorName, String genreName) {
        return impl.create(updateBook(Book.builder().build(), name, description, authorName, genreName));
    }

    @ShellMethod(value = "Изменение книги. Если поле не изменяется - не указывайте его")
    public String modifyBook(String id,
                             @ShellOption(defaultValue = ShellOption.NULL) String name,
                             @ShellOption(defaultValue = ShellOption.NULL) String description,
                             @ShellOption(defaultValue = ShellOption.NULL) String authorName,
                             @ShellOption(defaultValue = ShellOption.NULL) String genreName) {
        return impl.update(id, b -> updateBook(b, name, description, authorName, genreName));
    }

    @ShellMethod(value = "Добавить комментарий")
    public String addComment(String bookId, String comment) {
        var book = impl.getRepository().findById(bookId).orElseThrow();
        var commentObj = new BookComment(commentAuthor, comment);
        book.getComments().add(commentObj);
        impl.getRepository().save(book);
        return commentObj.toString();
    }

    public Availability addCommentAvailability() {
        return commentAuthor != null
                ? Availability.available()
                : Availability.unavailable("Сначала представьтесь (myName)");
    }
}
