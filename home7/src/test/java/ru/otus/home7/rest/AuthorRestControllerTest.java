package ru.otus.home7.rest;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.assertj.core.util.Lists;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import ru.otus.home7.controller.TestUtils;
import ru.otus.home7.domain.Author;
import ru.otus.home7.repository.AuthorRepository;
import ru.otus.home7.rest.dto.AuthorConverters;

import java.util.List;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(AuthorRestController.class)
@DisplayName("Rest-контроллер авторов позволяет")
class AuthorRestControllerTest extends TestUtils {

    private final Author author = Author.builder().id(42).name("Ivanov").build();
    @MockBean
    private AuthorRepository repository;

    @Test
    @DisplayName("получить всех")
    void getAll() throws Exception {
        var authors = List.of(
                Author.builder().id(1).name("author1").build(),
                author);
        given(repository.findAll()).willReturn(authors);

        mvc.perform(get("/rest/author")).andExpect(status().isOk())
                .andExpect(jsonPath("$..id").value(Lists.newArrayList(1, 42)));
    }

    @Test
    @DisplayName("получить одного")
    void getOne() throws Exception {
        given(repository.findById(author.getId())).willReturn(Optional.of(author));

        mvc.perform(get("/rest/author/{id}", author.getId()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value(author.getName()));
    }

    @Test
    @DisplayName("сохранить")
    void save() throws Exception {
        var dto = AuthorConverters.toDto(author);
        var json = new ObjectMapper().writeValueAsString(dto);

        given(repository.save(eq(author))).willReturn(author);

        mvc.perform(post("/rest/author")
                .content(json)
                .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    @DisplayName("удалить")
    void deleteOne() throws Exception {
        mvc.perform(delete("/rest/author/42"))
                .andExpect(status().isOk());

        verify(repository).deleteById(42L);
    }
}
