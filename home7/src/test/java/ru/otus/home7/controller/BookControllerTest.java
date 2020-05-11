package ru.otus.home7.controller;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import ru.otus.home7.domain.Author;
import ru.otus.home7.domain.Book;
import ru.otus.home7.domain.BookComment;
import ru.otus.home7.domain.Genre;
import ru.otus.home7.repository.AuthorRepository;
import ru.otus.home7.repository.BookRepository;
import ru.otus.home7.repository.GenreRepository;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(BookController.class)
@DisplayName("Web-контроллер книг позволяет")
class BookControllerTest {
    private final Author author1 = Author.builder().id(11).name("Ivanov").build();
    private final Author author2 = Author.builder().id(12).name("Petrov").build();
    private final List<Author> authors = List.of(author1, author2);
    private final Genre genre1 = Genre.builder().id(21).name("g1").build();
    private final Genre genre2 = Genre.builder().id(22).name("g2").build();
    private final List<Genre> genres = List.of(genre1, genre2);
    private final Book book = Book.builder().id(1).name("b1").author(author1).genre(genre1).description("d1").build();
    @Autowired
    private MockMvc mvc;
    @MockBean
    private AuthorRepository authorRepository;
    @MockBean
    private GenreRepository genreRepository;
    @MockBean
    private BookRepository bookRepository;

    {
        book.getComments().add(new BookComment(21, "u1", "good"));
    }

    @BeforeEach
    void beforeEach() {
        given(authorRepository.findAll()).willReturn(authors);
        given(genreRepository.findAll()).willReturn(genres);
        given(bookRepository.findById(book.getId())).willReturn(Optional.of(book));
    }

    @Test
    @DisplayName("получить все книги")
    void books() throws Exception {
        var books = List.of(
                Book.builder().id(2).name("b2").author(author2).genre(genre2).build(),
                book);
        given(bookRepository.findAll()).willReturn(books);

        mvc.perform(get("/books")).andExpect(status().isOk())
                .andExpect(model().attribute("books", books))
                .andExpect(view().name("books"));
    }

    @Test
    @DisplayName("удалить книгу")
    void delete() throws Exception {
        mvc.perform(get("/book/delete").param("id", "42")).andExpect(redirectedUrl("/books"));
        verify(bookRepository).deleteById(42L);
    }

    @Test
    @DisplayName("получить полную информацию о книге")
    void info() throws Exception {
        mvc.perform(get("/book/info").param("id", Long.toString(book.getId()))).andExpect(status().isOk())
                .andExpect(model().attribute("book", book))
                .andExpect(view().name("book_info"));
    }

    @Test
    @DisplayName("добавить комментарий")
    void addComment() throws Exception {
        var c = new BookComment(0, "u2", "bad");

        mvc.perform(post("/book/add_comment")
                .param("bookId", Long.toString(book.getId()))
                .param("author", c.getAuthor())
                .param("comment", c.getComment()))
                .andExpect(redirectedUrl("/book/info?id=" + book.getId()));

        assertThat(book.getComments()).contains(c);
    }

    @Test
    @DisplayName("изменить книгу")
    void edit() throws Exception {
        mvc.perform(get("/book/edit").param("id", Long.toString(book.getId()))).andExpect(status().isOk())
                .andExpect(model().attribute("book", book))
                .andExpect(view().name("book_detail"));
    }

    @Test
    @DisplayName("создать книгу")
    void create() throws Exception {
        mvc.perform(get("/book/create")).andExpect(status().isOk())
                .andExpect(model().attribute("book", Book.builder().id(0).build()))
                .andExpect(view().name("book_detail"));
    }

    @Test
    @DisplayName("сохранить книгу")
    void save() throws Exception {
        var book2 = Book.builder().id(book.getId()).name("b2").description("d2")
                .author(Author.builder().id(author2.getId()).build())
                .genre(Genre.builder().id(genre2.getId()).build())
                .build();
        book2.getComments().addAll(book.getComments());

        mvc.perform(post("/book/save")
                .param("id", Long.toString(book.getId()))
                .param("name", book2.getName())
                .param("description", book2.getDescription())
                .param("author", Long.toString(book2.getAuthor().getId()))
                .param("genre", Long.toString(book2.getGenre().getId())))
                .andDo(print())
                .andExpect(redirectedUrl("/books"));

        verify(bookRepository).save(book2);
    }
}
