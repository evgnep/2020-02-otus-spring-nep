package ru.otus.home7.dao;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.springframework.context.annotation.Import;
import ru.otus.home7.domain.Author;
import ru.otus.home7.domain.Book;
import ru.otus.home7.domain.Genre;

import static org.assertj.core.api.Assertions.assertThat;


@JdbcTest
@Import({BookDaoJdbc.class, AuthorDaoJdbc.class, GenreDaoJdbc.class})
class BookDaoJdbcTest {

    @Autowired
    private Crud<Book> daoBook;

    @Autowired
    private Crud<Author> daoAuthor;

    @Autowired
    private Crud<Genre> daoGenre;

    private Author author1;
    private Genre genre1;

    @BeforeEach
    void beforeEach() {
        author1 = daoAuthor.readById(1);
        genre1 = daoGenre.readById(1);
    }

    @Test
    void count() {
        assertThat(daoBook.count()).isEqualTo(1);
    }

    @Test
    void create() {
        var book = Book.builder().id(2).name("b").description("bb").author(author1).genre(genre1).build();
        daoBook.create(book);
        assertThat(daoBook.count()).isEqualTo(2);
        assertThat(daoBook.readById(2)).isEqualTo(book);
    }

    @Test
    void update() {
        var author2 = Author.builder().id(2).name("x").build();
        daoAuthor.create(author2);

        var genre2 = Genre.builder().id(2).name("h").build();
        daoGenre.create(genre2);

        var book = daoBook.readById(1);
        book.setName("b");
        book.setAuthor(author2);
        book.setGenre(genre2);
        daoBook.update(book);
        assertThat(daoBook.count()).isEqualTo(1);
        assertThat(daoBook.readById(1)).isEqualTo(book);
    }

    @Test
    void delete() {
        daoBook.delete(1);
        assertThat(daoBook.count()).isEqualTo(0);
    }

    @Test
    void readAll() {
        Book book = Book.builder().id(2).name("b").description("bb").author(author1).genre(genre1).build();
        daoBook.create(book);
        var all = daoBook.readAll();
        assertThat(all).hasSize(2).contains(book);
    }
}