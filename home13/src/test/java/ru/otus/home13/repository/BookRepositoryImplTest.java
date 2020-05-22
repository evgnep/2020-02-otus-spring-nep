package ru.otus.home13.repository;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.mongodb.core.MongoTemplate;
import ru.otus.home13.domain.Author;
import ru.otus.home13.domain.Book;
import ru.otus.home13.domain.BookComment;
import ru.otus.home13.domain.Genre;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("Репозиторий книг")
@SpringBootTest
class BookRepositoryImplTest {

    @Autowired
    private BookRepository bookRepository;

    @Autowired
    private AuthorRepository authorRepository;

    @Autowired
    private GenreRepository genreRepository;

    @Autowired
    private MongoTemplate template;

    private Author author1, author2;
    private Genre genre1, genre2;
    private Book book1;

    @BeforeEach
    void beforeEach() {
        authorRepository.deleteAll();
        genreRepository.deleteAll();
        bookRepository.deleteAll();

        author1 = authorRepository.save(Author.builder().name("a").build());
        author2 = authorRepository.save(Author.builder().name("b").build());
        genre1 = genreRepository.save(Genre.builder().name("z").build());
        genre2 = genreRepository.save(Genre.builder().name("x").build());

        book1 = template.save(Book.builder().name("q").authors(List.of(author1)).genre(genre1)
                .comments(List.of(new BookComment("q", "ok"),
                        new BookComment("w", "bad"))
                ).build());
    }

    @Test
    @DisplayName("по умолчанию содержит 1 книгу")
    void count() {
        assertThat(bookRepository.count()).isEqualTo(1);
    }

    @Test
    @DisplayName("можно создать новую")
    void create() {
        var book = Book.builder().name("b").description("bb").authors(List.of(author1)).genre(genre1).build();
        bookRepository.save(book);
        assertThat(bookRepository.count()).isEqualTo(2);
        assertThat(bookRepository.findById(book.getId()).orElseThrow()).isEqualTo(book);
    }

    @Test
    @DisplayName("можно обновить")
    void update() {
        var book = bookRepository.findById(book1.getId()).orElseThrow();
        book.setName("b");
        book.getAuthors().set(0, author2);
        book.setGenre(genre2);
        bookRepository.save(book);
        assertThat(bookRepository.count()).isEqualTo(1);
        assertThat(bookRepository.findById(book1.getId()).orElseThrow()).isEqualTo(book);
    }

    @Test
    @DisplayName("можно удалить")
    void delete() {
        bookRepository.deleteById(book1.getId());
        assertThat(bookRepository.count()).isEqualTo(0);
    }

    @Test
    @DisplayName("можно прочитать все")
    void readAll() {
        Book book = Book.builder().name("b").description("bb").authors(List.of(author1)).genre(genre1).build();
        bookRepository.save(book);
        var all = bookRepository.findAll();
        assertThat(all).hasSize(2).contains(book);
    }

    @Test
    @DisplayName("чтение с комментариями")
    void readComments() {
        var book = bookRepository.findById(book1.getId()).orElseThrow();
        assertThat(book).isNotNull();
        assertThat(book.getComments()).hasSize(2).anyMatch(x -> x.getAuthor().equals("q")).anyMatch(x -> x.getComment().equals("bad"));
    }

    @Test
    @DisplayName("удаление комментария")
    void deleteComment() {
        var book = bookRepository.findById(book1.getId()).orElseThrow();
        book.getComments().remove(0);
        bookRepository.save(book);

        book = bookRepository.findById(book1.getId()).orElseThrow();
        assertThat(book.getComments().size()).isEqualTo(1);
    }

    @Test
    @DisplayName("новый комментарий")
    void addComment() {
        var book = bookRepository.findById(book1.getId()).orElseThrow();
        book.getComments().add(new BookComment("e", "some"));
        bookRepository.save(book);

        book = bookRepository.findById(book1.getId()).orElseThrow();
        assertThat(book.getComments().size()).isEqualTo(3);
    }
}
