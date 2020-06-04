package ru.otus.home20;

import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Service;
import ru.otus.home20.domain.Author;
import ru.otus.home20.domain.Book;
import ru.otus.home20.domain.BookComment;
import ru.otus.home20.domain.Genre;

@Service
@ConditionalOnProperty(name = "hasInitialData", matchIfMissing = true)
public class InitialData {

    public InitialData(MongoTemplate template) {
        var author1 = template.save(Author.builder().name("Ivanov").id(1).build());
        var author2 = template.save(Author.builder().name("Petrov").id(2).build());

        var genre1 = template.save(Genre.builder().name("Детектив").id(10).build());
        var genre2 = template.save(Genre.builder().name("Фантастика").id(11).build());

        var book1 = Book.builder().name("Убийство в саду").description("Интересный детектив").genre(genre1).id(20).build();
        book1.getAuthors().add(author1);
        book1.getAuthors().add(author2);
        book1.getComments().add(new BookComment("Сидоров", "Очень интересно"));
        template.save(book1);

        var book2 = Book.builder().name("Оно").genre(genre2).id(21).build();
        book2.getAuthors().add(author2);
        template.save(book2);
    }
}
