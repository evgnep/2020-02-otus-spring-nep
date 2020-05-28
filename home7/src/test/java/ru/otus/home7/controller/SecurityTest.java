package ru.otus.home7.controller;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.test.web.servlet.MockMvc;
import ru.otus.home7.repository.AuthorRepository;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.redirectedUrlPattern;

@WebMvcTest(AuthorController.class)
@DisplayName("Web-контроллер авторов без авторизации")
class SecurityTest {
    @Autowired
    private MockMvc mvc;

    @MockBean
    private AuthorRepository repository;

    @MockBean
    private UserDetailsService userDetailsService;

    @Test
    @DisplayName("должен предлагать логин")
    void list() throws Exception {
        mvc.perform(get("/authors")).andExpect(redirectedUrlPattern("**/login"));
    }
}
