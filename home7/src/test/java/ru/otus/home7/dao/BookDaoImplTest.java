package ru.otus.home7.dao;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;
import ru.otus.home7.domain.Author;
import ru.otus.home7.domain.Book;
import ru.otus.home7.domain.BookComment;
import ru.otus.home7.domain.Genre;

import javax.persistence.EntityManager;

import static org.assertj.core.api.Assertions.assertThat;


@DataJpaTest
@Import({BookDaoImpl.class, AuthorDaoImpl.class, GenreDaoImpl.class})
@DisplayName("Dao книг")
class BookDaoImplTest {

    @Autowired
    private Dao<Book> daoBook;

    @Autowired
    private Dao<Author> daoAuthor;

    @Autowired
    private Dao<Genre> daoGenre;

    @Autowired
    private EntityManager em;

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
        var book = Book.builder().name("b").description("bb").author(author1).genre(genre1).build();
        daoBook.save(book);
        assertThat(daoBook.count()).isEqualTo(2);
        assertThat(daoBook.readById(book.getId())).isEqualTo(book);
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
        Book book = Book.builder().name("b").description("bb").author(author1).genre(genre1).build();
        daoBook.save(book);
        var all = daoBook.readAll();
        assertThat(all).hasSize(2).contains(book);
    }

    @Test
    @DisplayName("чтение с комментариями")
    void readComments() {
        var book = daoBook.readById(1);
        assertThat(book).isNotNull();
        assertThat(book.getComments()).hasSize(2).anyMatch(x -> x.getAuthor().equals("x")).anyMatch(x -> x.getComment().equals("other_comment"));
    }

    @Test
    @DisplayName("удаление комментария")
    void deleteComment() {
        var book = daoBook.readById(1);
        book.getComments().remove(0);

        assertThat(em.createQuery("select count(c) from BookComment c", Long.class).getSingleResult()).isEqualTo(1);
    }

    @Test
    @DisplayName("новый комментарий")
    void addComment() {
        var book = daoBook.readById(1);
        book.getComments().add(BookComment.builder().author("w").comment("aaa").build());

        assertThat(em.createQuery("select count(c) from BookComment c", Long.class).getSingleResult()).isEqualTo(3);
    }

    @Test
    @DisplayName("изменение комментария")
    void updateComment() {
        var book = daoBook.readById(1);
        var comment = book.getComments().get(0);
        comment.setComment("bbbb");

        var query = em.createQuery("select c.comment from BookComment c where c.id = :id", String.class);
        query.setParameter("id", comment.getId());
        assertThat(query.getSingleResult()).isEqualTo("bbbb");
    }
}
