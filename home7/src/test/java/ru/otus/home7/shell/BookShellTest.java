package ru.otus.home7.shell;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;
import ru.otus.home7.domain.Author;
import ru.otus.home7.domain.Book;
import ru.otus.home7.domain.Genre;
import ru.otus.home7.repository.AuthorRepository;
import ru.otus.home7.repository.BookRepository;
import ru.otus.home7.repository.GenreRepository;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@Transactional
class BookShellTest {

    @Autowired
    BookShell shell;

    @Autowired
    BookRepository bookRepository;

    @Autowired
    AuthorRepository authorRepository;

    @Autowired
    GenreRepository genreRepository;

    @Test
    void readAllBooks() {
        var book1 = bookRepository.findById(1L).orElseThrow();
        assertThat(shell.readAllBooks()).contains(book1.toString());
    }

    @Test
    void readBookById() {
        var book1 = bookRepository.findById(1L).orElseThrow();
        assertThat(shell.readBookById(1)).contains(book1.toString());
    }

    @Test
    void bookCount() {
        assertThat(shell.bookCount()).isEqualTo("1");
    }

    @Test
    void deleteBook() {
        shell.deleteBook(1);
        assertThat(bookRepository.count()).isEqualTo(0);
    }

    @Test
    void createBook() {
        var author1 = authorRepository.findById(1L).orElseThrow();
        var genre1 = genreRepository.findById(1L).orElseThrow();
        shell.createBook("asd", "---", author1.getName(), genre1.getName());

        var book = bookRepository.findAll().get(1);
        assertThat(book).isEqualTo(Book.builder().id(book.getId()).name("asd").description("---").author(author1).genre(genre1).build());
    }

    @Test
    void modifyBook() {
        var author2 = Author.builder().name("w").build();
        var genre2 = Genre.builder().name("x").build();
        authorRepository.save(author2);
        genreRepository.save(genre2);
        var book = bookRepository.findById(1L).orElseThrow();

        shell.modifyBook(1, null, null, author2.getName(), null);
        book.setAuthor(author2);
        assertThat(bookRepository.findById(1L).orElseThrow()).isEqualTo(book);

        shell.modifyBook(1, "dsa", "===", null, genre2.getName());
        book.setName("dsa");
        book.setDescription("===");
        book.setGenre(genre2);
        assertThat(bookRepository.findById(1L).orElseThrow()).isEqualTo(book);
    }

    @Test
    void addComment() {
        var book = bookRepository.findById(1L).orElseThrow();
        book.getComments().clear();

        shell.myName("test");
        shell.addComment(1, "comment");

        assertThat(book.getComments()).hasSize(1).allMatch(c -> c.getAuthor().equals("test") && c.getComment().equals("comment"));
    }
}
