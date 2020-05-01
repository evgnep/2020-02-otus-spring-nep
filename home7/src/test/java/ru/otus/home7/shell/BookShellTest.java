package ru.otus.home7.shell;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.transaction.annotation.Transactional;
import ru.otus.home7.dao.Dao;
import ru.otus.home7.domain.Author;
import ru.otus.home7.domain.Book;
import ru.otus.home7.domain.Genre;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@Transactional
@Sql("classpath:data-test.sql")
class BookShellTest {

    @Autowired
    BookShell shell;

    @Autowired
    Dao<Book> bookDao;

    @Autowired
    Dao<Author> authorDao;

    @Autowired
    Dao<Genre> genreDao;

    @Test
    void readAllBooks() {
        var book1 = bookDao.readById(1);
        assertThat(shell.readAllBooks()).contains(book1.toString());
    }

    @Test
    void readBookById() {
        var book1 = bookDao.readById(1);
        assertThat(shell.readBookById(1)).contains(book1.toString());
    }

    @Test
    void bookCount() {
        assertThat(shell.bookCount()).isEqualTo("1");
    }

    @Test
    void deleteBook() {
        shell.deleteBook(1);
        assertThat(bookDao.count()).isEqualTo(0);
    }

    @Test
    void createBook() {
        var author1 = authorDao.readById(1);
        var genre1 = genreDao.readById(1);
        shell.createBook(2, "asd", "---", author1.getName(), genre1.getName());

        var book = bookDao.readById(2);
        assertThat(book).isEqualTo(Book.builder().id(2).name("asd").description("---").author(author1).genre(genre1).build());
    }

    @Test
    void modifyBook() {
        var author2 = Author.builder().id(2).name("w").build();
        var genre2 = Genre.builder().id(2).name("x").build();
        authorDao.save(author2);
        genreDao.save(genre2);
        var book = bookDao.readById(1);

        shell.modifyBook(1, null, null, author2.getName(), null);
        book.setAuthor(author2);
        assertThat(bookDao.readById(1)).isEqualTo(book);

        shell.modifyBook(1, "dsa", "===", null, genre2.getName());
        book.setName("dsa");
        book.setDescription("===");
        book.setGenre(genre2);
        assertThat(bookDao.readById(1)).isEqualTo(book);
    }
}
