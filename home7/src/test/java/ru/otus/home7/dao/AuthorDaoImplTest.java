package ru.otus.home7.dao;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;
import ru.otus.home7.domain.Author;

import static org.assertj.core.api.Assertions.assertThat;


@DataJpaTest
@Import(AuthorDaoImpl.class)
@DisplayName("Dao авторов")
class AuthorDaoImplTest {

    @Autowired
    private Dao<Author> dao;

    @Test
    @DisplayName("по умолчанию 1 автор")
    void count() {
        assertThat(dao.count()).isEqualTo(1);
    }

    @Test
    @DisplayName("можно создать нового")
    void create() {
        Author author = Author.builder().name("x").build();
        dao.save(author);
        assertThat(dao.count()).isEqualTo(2);
        assertThat(dao.readById(author.getId())).isEqualTo(author);
    }

    @Test
    @DisplayName("можно изменить имеющегося")
    void update() {
        var author = dao.readById(1);
        author.setName("b");
        dao.save(author);
        assertThat(dao.count()).isEqualTo(1);
        assertThat(dao.readById(1)).isEqualTo(author);
    }

    @Test
    @DisplayName("можно удалить")
    void delete() {
        dao.delete(1);
        assertThat(dao.count()).isEqualTo(0);
    }

    @Test
    @DisplayName("можно прочитать все")
    void readAll() {
        Author author = Author.builder().name("x").build();
        dao.save(author);
        var all = dao.readAll();
        assertThat(all).hasSize(2).contains(author);
    }
}
