package ru.otus.home7.dao;

import org.springframework.jdbc.core.namedparam.NamedParameterJdbcOperations;
import org.springframework.stereotype.Repository;
import ru.otus.home7.domain.Author;
import ru.otus.home7.domain.Book;
import ru.otus.home7.domain.Genre;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;

@Repository
public class BookDaoJdbc extends AbstractDaoJdbc<Book> {
    public BookDaoJdbc(NamedParameterJdbcOperations jdbc) {
        super(jdbc, new BookMapper(), List.of("name", "description", "author", "genre"), "book",
                "SELECT book.id as id, book.name as name, book.description as description, book.author as author, " +
                        "a.id as a_id, a.name as a_name, " +
                        "g.id as g_id, g.name as g_name " +
                        "FROM book inner join author a on (book.author = a.id) inner join genre g on (book.genre = g.id) %s " +
                        "ORDER BY book.id");
    }

    private static class BookMapper implements Mapper<Book> {
        @Override
        public Book mapRow(ResultSet rs, int rowNum) throws SQLException {
            var author = Author.builder().id(rs.getLong("a_id")).name(rs.getString("a_name")).build();
            var genre = Genre.builder().id(rs.getLong("g_id")).name(rs.getString("g_name")).build();
            return Book.builder()
                    .id(rs.getLong("id"))
                    .name(rs.getString("name"))
                    .description(rs.getString("description"))
                    .author(author)
                    .genre(genre)
                    .build();
        }

        @Override
        public Map<String, Object> toMap(Book b) {
            return Map.of("id", b.getId(),
                    "name", b.getName(),
                    "description", b.getDescription(),
                    "author", b.getAuthor().getId(),
                    "genre", b.getGenre().getId());
        }
    }
}
