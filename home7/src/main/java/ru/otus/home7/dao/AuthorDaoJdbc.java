package ru.otus.home7.dao;

import org.springframework.jdbc.core.namedparam.NamedParameterJdbcOperations;
import org.springframework.stereotype.Repository;
import ru.otus.home7.domain.Author;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;

@Repository
public class AuthorDaoJdbc extends AbstractDaoJdbc<Author> implements AuthorDao {
    public AuthorDaoJdbc(NamedParameterJdbcOperations jdbc) {
        super(jdbc, new AuthorMapper(), List.of("name"), "author", null);
    }

    @Override
    public Author readByName(String name) {
        return readOneByCondition("WHERE name = :name", Map.of("name", name));
    }

    private static class AuthorMapper implements Mapper<Author> {
        @Override
        public Author mapRow(ResultSet rs, int rowNum) throws SQLException {
            return Author.builder()
                    .id(rs.getLong("id"))
                    .name(rs.getString("name"))
                    .build();
        }

        @Override
        public Map<String, Object> toMap(Author a) {
            return Map.of("id", a.getId(),
                    "name", a.getName());
        }
    }
}
