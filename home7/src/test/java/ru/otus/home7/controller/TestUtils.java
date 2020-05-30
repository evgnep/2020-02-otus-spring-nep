package ru.otus.home7.controller;

import org.junit.jupiter.api.BeforeEach;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;


@WithMockUser(username = "admin")
public class TestUtils {
    protected MockMvc mvc;
    @MockBean
    private UserDetailsService userDetailsService;
    @Autowired
    private WebApplicationContext webApplicationContext;

    @BeforeEach
    void initMvc() {
        mvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
    }
}
