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
        var author1 = t.save(Author.builder().name("Иванов").build());
        var author2 = t.save(Author.builder().name("Петров").build());

        var genre1 = t.save(Genre.builder().name("Детектив").build());
        var genre2 = t.save(Genre.builder().name("Фантастика").build());

        var book1 = Book.builder().name("Убийство в саду").description("Интересный детектив").genre(genre1).build();
        book1.getAuthors().add(author1);
        book1.getAuthors().add(author2);
        book1.getComments().add(new BookComment("Сидоров", "Очень интересно"));
        t.save(book1);

        var book2 = Book.builder().name("Оно").genre(genre2).build();
        book2.getAuthors().add(author2);
        t.save(book2);
    }
}
