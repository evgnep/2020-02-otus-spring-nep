package ru.otus.home20.controller;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.data.mongodb.core.MongoTemplate;
import ru.otus.home20.controller.dto.BookConverters;
import ru.otus.home20.domain.Author;
import ru.otus.home20.domain.Book;
import ru.otus.home20.domain.Genre;
import ru.otus.home7.rest.dto.AuthorDto;
import ru.otus.home7.rest.dto.BookDto;
import ru.otus.home7.rest.dto.GenreDto;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class BookControllerIntegrationTest {

    private final String URL = "/rest/book";
    Author author1;
    Genre genre1;
    Book book1;
    @Autowired
    private TestRestTemplate rest;
    @Autowired
    private MongoTemplate template;

    @BeforeEach
    void beforeEach() {
        author1 = template.save(Author.builder().name("Ivanov").id(1).build());

        genre1 = template.save(Genre.builder().name("Детектив").id(10).build());

        book1 = Book.builder().name("Убийство в саду").description("Интересный детектив").genre(genre1).id(20).build();
        book1.getAuthors().add(author1);
        template.save(book1);
    }

    @Test
    void getOne() throws Exception {
        var res = rest.getForObject(URL + "/{id}", BookDto.class, book1.getId());

        assertThat(res.getName()).isEqualTo(book1.getName());
        assertThat(res.getAuthor().getName()).isEqualTo(author1.getName());
    }

    @Test
    void insert() {
        var saved = new BookDto()
                .setName("some name")
                .setDescription("descr")
                .setAuthor(new AuthorDto().setId(author1.getId()))
                .setGenre(new GenreDto().setId(genre1.getId()));

        var res = rest.postForObject(URL, saved, BookDto.class);

        assertThat(res.getId()).isEqualTo(book1.getId() + 1);
        assertThat(res.getAuthor().getName()).isEqualTo(author1.getName());
        assertThat(template.findById(res.getId(), Book.class)).isNotNull().matches(b -> b.getName().equals(saved.getName()));
    }

    @Test
    void update() {
        var updated = BookConverters.toDto(book1)
                .setName("some name")
                .setDescription("other desc");

        rest.put(URL, updated);

        assertThat(template.findById(book1.getId(), Book.class)).matches(b -> b.getName().equals(updated.getName()));
    }

    @Test
    void delete() {
        rest.delete(URL + "/{id}", book1.getId());

        assertThat(template.findAll(Book.class)).isEmpty();
    }
}