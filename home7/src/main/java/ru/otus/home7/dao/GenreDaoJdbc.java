package ru.otus.home7.dao;

import org.springframework.jdbc.core.namedparam.NamedParameterJdbcOperations;
import org.springframework.stereotype.Repository;
import ru.otus.home7.domain.Genre;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;

@Repository
public class GenreDaoJdbc extends AbstractDaoJdbc<Genre> implements GenreDao {
    public GenreDaoJdbc(NamedParameterJdbcOperations jdbc) {
        super(jdbc, new GenreMapper(), List.of("name"), "genre", null);
    }

    @Override
    public Genre readByName(String name) {
        return readOneByCondition("WHERE name = :name", Map.of("name", name));
    }

    private static class GenreMapper implements Mapper<Genre> {
        @Override
        public Genre mapRow(ResultSet rs, int rowNum) throws SQLException {
            return Genre.builder()
                    .id(rs.getLong("id"))
                    .name(rs.getString("name"))
                    .build();
        }

        @Override
        public Map<String, Object> toMap(Genre g) {
            return Map.of("id", g.getId(),
                    "name", g.getName());
        }
    }
}
