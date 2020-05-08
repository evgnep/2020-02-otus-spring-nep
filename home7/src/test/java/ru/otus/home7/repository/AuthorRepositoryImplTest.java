package ru.otus.home7.repository;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import ru.otus.home7.domain.Author;

import static org.assertj.core.api.Assertions.assertThat;


@DataJpaTest
@DisplayName("Репозиторий авторов")
class AuthorRepositoryImplTest {

    @Autowired
    private AuthorRepository repository;

    @Test
    @DisplayName("по умолчанию 1 автор")
    void count() {
        assertThat(repository.count()).isEqualTo(1);
    }

    @Test
    @DisplayName("можно создать нового")
    void create() {
        Author author = Author.builder().name("x").build();
        repository.save(author);
        assertThat(repository.count()).isEqualTo(2);
        assertThat(repository.findById(author.getId()).orElseThrow()).isEqualTo(author);
    }

    @Test
    @DisplayName("можно изменить имеющегося")
    void update() {
        var author = repository.findById(1L).orElseThrow();
        author.setName("b");
        repository.save(author);
        assertThat(repository.count()).isEqualTo(1);
        assertThat(repository.findById(1L).orElseThrow()).isEqualTo(author);
    }

    @Test
    @DisplayName("можно удалить")
    void delete() {
        repository.deleteById(1L);
        assertThat(repository.count()).isEqualTo(0);
    }

    @Test
    @DisplayName("можно прочитать все")
    void readAll() {
        Author author = Author.builder().name("x").build();
        repository.save(author);
        var all = repository.findAll();
        assertThat(all).hasSize(2).contains(author);
    }
}
