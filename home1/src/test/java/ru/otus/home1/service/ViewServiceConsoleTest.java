package ru.otus.home1.service;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.stereotype.Component;
import ru.otus.home1.Properties;
import ru.otus.home1.domain.User;

import java.io.ByteArrayInputStream;
import java.io.PrintStream;
import java.util.Locale;

import static org.assertj.core.api.BDDAssertions.then;
import static org.mockito.Mockito.mock;

@SpringBootTest
class ViewServiceConsoleTest {
    @Autowired
    StubProperties properties;
    @Autowired
    ViewService service;

    @Test
    void askUser() {
        properties.getInputStream().setString("Ivan\nPetrov\n");

        var user = service.askUser();
        then(user).isEqualTo(new User("Ivan", "Petrov"));
    }

    @Test
    void askQuestion() {
    }

    @Test
    void showReport() {
    }

    private static class StubInputStream extends ByteArrayInputStream {
        public StubInputStream() {
            super(new byte[0]);
        }

        public void setString(String s) {
            buf = s.getBytes();
            pos = 0;
            count = buf.length;
            mark = 0;
        }
    }

    @Component
    static class StubProperties extends Properties {
        StubProperties() {
            super(Locale.US, new StubInputStream(), mock(PrintStream.class));
        }

        public StubInputStream getInputStream() {
            return (StubInputStream) super.getInputStream();
        }
    }

    @Configuration
    @Import({ViewServiceConsole.class, StubProperties.class})
    static class Config {
    }
}
