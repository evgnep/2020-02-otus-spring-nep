package ru.otus.home7.dao;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.jdbc.Sql;
import ru.otus.home7.domain.Author;
import ru.otus.home7.domain.Book;
import ru.otus.home7.domain.Genre;

import static org.assertj.core.api.Assertions.assertThat;


@DataJpaTest
@Import({BookDaoImpl.class, AuthorDaoImpl.class, GenreDaoImpl.class})
@DisplayName("Dao книг")
@Sql("classpath:data-test.sql")
class BookDaoImplTest {

    @Autowired
    private Dao<Book> daoBook;

    @Autowired
    private Dao<Author> daoAuthor;

    @Autowired
    private Dao<Genre> daoGenre;

    private Author author1;
    private Genre genre1;

    @BeforeEach
    void beforeEach() {
        author1 = daoAuthor.readById(1);
        genre1 = daoGenre.readById(1);
    }

    @Test
    @DisplayName("по умолчанию содержит 1 книгу")
    void count() {
        assertThat(daoBook.count()).isEqualTo(1);
    }

    @Test
    @DisplayName("можно создать новую")
    void create() {
        var book = Book.builder().id(2).name("b").description("bb").author(author1).genre(genre1).build();
        daoBook.save(book);
        assertThat(daoBook.count()).isEqualTo(2);
        assertThat(daoBook.readById(2)).isEqualTo(book);
    }

    @Test
    @DisplayName("можно обновить")
    void update() {
        var author2 = daoAuthor.save(Author.builder().id(2).name("x").build());
        var genre2 = daoGenre.save(Genre.builder().id(2).name("h").build());

        var book = daoBook.readById(1);
        book.setName("b");
        book.setAuthor(author2);
        book.setGenre(genre2);
        daoBook.save(book);
        assertThat(daoBook.count()).isEqualTo(1);
        assertThat(daoBook.readById(1)).isEqualTo(book);
    }

    @Test
    @DisplayName("можно удалить")
    void delete() {
        daoBook.delete(1);
        assertThat(daoBook.count()).isEqualTo(0);
    }

    @Test
    @DisplayName("можно прочитать все")
    void readAll() {
        Book book = Book.builder().id(2).name("b").description("bb").author(author1).genre(genre1).build();
        daoBook.save(book);
        var all = daoBook.readAll();
        assertThat(all).hasSize(2).contains(book);
    }
}
