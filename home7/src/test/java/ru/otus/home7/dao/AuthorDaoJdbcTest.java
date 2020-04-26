package ru.otus.home7.dao;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.springframework.context.annotation.Import;
import ru.otus.home7.domain.Author;

import static org.assertj.core.api.Assertions.assertThat;


@JdbcTest
@Import(AuthorDaoJdbc.class)
class AuthorDaoJdbcTest {

    @Autowired
    private Crud<Author> dao;

    @Test
    void count() {
        assertThat(dao.count()).isEqualTo(1);
    }

    @Test
    void create() {
        Author author = Author.builder().id(2).name("x").build();
        dao.create(author);
        assertThat(dao.count()).isEqualTo(2);
        assertThat(dao.readById(2)).isEqualTo(author);
    }

    @Test
    void update() {
        var author = dao.readById(1);
        author.setName("b");
        dao.update(author);
        assertThat(dao.count()).isEqualTo(1);
        assertThat(dao.readById(1)).isEqualTo(author);
    }

    @Test
    void delete() {
        dao.delete(1);
        assertThat(dao.count()).isEqualTo(0);
    }

    @Test
    void readAll() {
        Author author = Author.builder().id(2).name("x").build();
        dao.create(author);
        var all = dao.readAll();
        assertThat(all).hasSize(2).contains(author);
    }
}