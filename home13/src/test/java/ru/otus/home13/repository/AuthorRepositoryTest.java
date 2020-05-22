package ru.otus.home13.repository;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.mongodb.core.MongoTemplate;
import ru.otus.home13.domain.Author;

import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("Репозиторий авторов")
@SpringBootTest
class AuthorRepositoryTest {

    @Autowired
    private AuthorRepository repository;

    @Autowired
    private MongoTemplate template;

    private Author author1;

    @BeforeEach
    void init() {
        template.getCollection("Author").drop();
        author1 = template.save(Author.builder().name("Ivanov").build());
    }

    @Test
    @DisplayName("по умолчанию 1 автор")
    void count() {
        assertThat(repository.count()).isEqualTo(1);
    }

    @Test
    @DisplayName("можно создать нового")
    void create() {
        Author author2 = Author.builder().name("x").build();
        repository.save(author2);
        assertThat(repository.count()).isEqualTo(2);
        assertThat(repository.findById(author2.getId()).orElseThrow()).isEqualTo(author2);
    }

    @Test
    @DisplayName("можно изменить имеющегося")
    void update() {
        var author = repository.findById(author1.getId()).orElseThrow();
        author.setName("b");
        repository.save(author);
        assertThat(repository.count()).isEqualTo(1);
        assertThat(repository.findById(author1.getId()).orElseThrow()).isEqualTo(author);
    }

    @Test
    @DisplayName("можно удалить")
    void delete() {
        repository.deleteById(author1.getId());
        assertThat(repository.count()).isEqualTo(0);
    }

    @Test
    @DisplayName("можно прочитать все")
    void readAll() {
        Author author2 = Author.builder().name("x").build();
        repository.save(author2);
        var all = repository.findAll();
        assertThat(all).hasSize(2).contains(author2, author1);
    }
}