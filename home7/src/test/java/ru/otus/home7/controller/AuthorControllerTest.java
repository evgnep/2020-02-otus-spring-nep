package ru.otus.home7.controller;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import ru.otus.home7.domain.Author;
import ru.otus.home7.repository.AuthorRepository;

import java.util.List;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(AuthorController.class)
@DisplayName("Web-контроллер авторов")
class AuthorControllerTest extends TestUtils {
    private final Author author = Author.builder().id(42).name("Ivanov").build();

    @MockBean
    private AuthorRepository repository;

    @Test
    @DisplayName("отображает всех авторов")
    void list() throws Exception {
        var authors = List.of(
                Author.builder().id(1).name("author1").build(),
                author);
        given(repository.findAll()).willReturn(authors);

        mvc.perform(get("/authors")).andExpect(status().isOk())
                .andExpect(model().attribute("authors", authors))
                .andExpect(view().name("authors"));
    }

    @Test
    @DisplayName("можно удалить")
    void delete() throws Exception {
        mvc.perform(get("/author/delete").param("id", "42")).andExpect(redirectedUrl("/authors"));
        verify(repository).deleteById(42L);
    }

    @Test
    @DisplayName("можно создать")
    void create() throws Exception {
        mvc.perform(get("/author/create")).andExpect(status().isOk())
                .andExpect(model().attribute("author", Author.builder().id(0).build()))
                .andExpect(view().name("author_detail"));
    }

    @Test
    @DisplayName("можно редактировать")
    void edit() throws Exception {
        given(repository.findById(author.getId())).willReturn(Optional.of(author));

        mvc.perform(get("/author/edit").param("id", Long.toString(author.getId()))).andExpect(status().isOk())
                .andExpect(model().attribute("author", author))
                .andExpect(view().name("author_detail"));
    }

    @Test
    @DisplayName("можно сохранить")
    void save() throws Exception {
        given(repository.findById(author.getId())).willReturn(Optional.of(author));
        mvc.perform(get("/author/edit").param("id", Long.toString(author.getId()))).andExpect(status().isOk());

        mvc.perform(post("/author/save")
                .param("id", Long.toString(author.getId())).param("name", author.getName()))
                .andExpect(redirectedUrl("/authors"));
        verify(repository).save(eq(author));
    }
}
