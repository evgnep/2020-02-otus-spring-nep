package ru.otus.home13;

import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Service;
import ru.otus.home13.domain.Author;
import ru.otus.home13.domain.Book;
import ru.otus.home13.domain.BookComment;
import ru.otus.home13.domain.Genre;

@Service
@ConditionalOnProperty(name = "hasInitialData", matchIfMissing = true)
public class InitialData {

    public InitialData(MongoTemplate t) {
        var a1 = t.save(Author.builder().name("Ivanov").build());
        var a2 = t.save(Author.builder().name("Petrov").build());

        var g1 = t.save(Genre.builder().name("Детектив").build());
        var g2 = t.save(Genre.builder().name("Фантастика").build());

        var b1 = Book.builder().name("Убийство в саду").description("Интересный детектив").genre(g1).build();
        b1.getAuthors().add(a1);
        b1.getAuthors().add(a2);
        b1.getComments().add(new BookComment("Сидоров", "Очень интересно"));
        t.save(b1);

        var b2 = Book.builder().name("Оно").genre(g2).build();
        b2.getAuthors().add(a2);
        t.save(b2);
    }
}
