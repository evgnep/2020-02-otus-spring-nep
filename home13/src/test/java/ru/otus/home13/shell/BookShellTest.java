package ru.otus.home13.shell;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import ru.otus.home13.domain.Author;
import ru.otus.home13.domain.Book;
import ru.otus.home13.domain.BookComment;
import ru.otus.home13.domain.Genre;
import ru.otus.home13.repository.AuthorRepository;
import ru.otus.home13.repository.BookRepository;
import ru.otus.home13.repository.GenreRepository;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
class BookShellTest {

    @Autowired
    BookShell shell;

    @Autowired
    BookRepository bookRepository;

    @Autowired
    AuthorRepository authorRepository;

    @Autowired
    GenreRepository genreRepository;

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

        book1 = bookRepository.save(Book.builder().name("q").authors(List.of(author1)).genre(genre1)
                .comments(List.of(new BookComment("q", "ok"),
                        new BookComment("w", "bad"))
                ).build());
    }

    @Test
    void readAllBooks() {
        assertThat(shell.readAllBooks()).contains(book1.toString());
    }

    @Test
    void readBookById() {
        assertThat(shell.readBookById(book1.getId())).contains(book1.toString());
    }

    @Test
    void bookCount() {
        assertThat(shell.bookCount()).isEqualTo("1");
    }

    @Test
    void deleteBook() {
        shell.deleteBook(book1.getId());
        assertThat(bookRepository.count()).isEqualTo(0);
    }

    @Test
    void createBook() {
        bookRepository.deleteAll();

        shell.createBook("asd", "---", author1.getName(), genre1.getName());

        var book = bookRepository.findAll().iterator().next();
        assertThat(book).isEqualTo(Book.builder().id(book.getId()).name("asd").description("---").authors(List.of(author1)).genre(genre1).build());
    }

    @Test
    void modifyBook() {
        shell.modifyBook(book1.getId(), null, null, author2.getName(), null);
        book1.setAuthors(new ArrayList<>());
        book1.getAuthors().add(author2);
        assertThat(bookRepository.findById(book1.getId()).orElseThrow()).isEqualTo(book1);

        shell.modifyBook(book1.getId(), "dsa", "===", null, genre2.getName());
        book1.setName("dsa");
        book1.setDescription("===");
        book1.setGenre(genre2);
        assertThat(bookRepository.findById(book1.getId()).orElseThrow()).isEqualTo(book1);
    }

    @Test
    void addComment() {
        book1.setComments(new ArrayList<>());
        bookRepository.save(book1);

        shell.myName("test");
        shell.addComment(book1.getId(), "comment");

        book1 = bookRepository.findById(book1.getId()).orElseThrow();
        assertThat(book1.getComments()).hasSize(1).allMatch(c -> c.getAuthor().equals("test") && c.getComment().equals("comment"));
    }
}
