package ru.otus.home7.dao;

import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcOperations;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * Базовый DAO на основе JDBC
 * ! Я видел вашу рекомендацию так не делать, но это существенно сократило объем писанины !
 * Я понимаю, что этот класс недостаточно гибок и не рассчитан на все случаи жизни, однако нужды этого приложения
 * он покрывает.
 */
public abstract class AbstractDaoJdbc<T> implements Crud<T> {
    private final NamedParameterJdbcOperations jdbc;
    private final Mapper<T> mapper;
    private final String selectSql, select1Sql, insertSql, updateSql, deleteSql, countSql, selectWhereSql;

    /**
     * @param mapper    {@see Mapper}
     * @param fields    список полей таблицы, без поля id (первичный ключ должен называться id и быть целым)
     * @param table     имя таблицы
     * @param selectSql кастомный sql SELECT (если нужны связи и т.п.), должен содержать %s на месте WHERE. Если null - генерируется автоматически.
     */
    protected AbstractDaoJdbc(NamedParameterJdbcOperations jdbc, Mapper<T> mapper, List<String> fields, String table, String selectSql) {
        this.jdbc = jdbc;
        this.mapper = mapper;

        if (selectSql == null)
            selectWhereSql = new StringBuilder().append("select id, ").append(String.join(", ", fields))
                    .append(" from ").append(table)
                    .append(" %s order by id")
                    .toString();
        else
            selectWhereSql = selectSql;

        this.selectSql = String.format(selectWhereSql, "");
        this.select1Sql = String.format(selectWhereSql, " WHERE " + table + ".id = :id");

        this.insertSql = new StringBuilder().append("insert into ").append(table)
                .append("(id, ").append(String.join(", ", fields)).append(") ")
                .append("values (:id, ")
                .append(fields.stream().map(n -> ":" + n).collect(Collectors.joining(", ")))
                .append(")").toString();

        this.updateSql = new StringBuilder().append("update ").append(table)
                .append(" set ").append(fields.stream().map(n -> n + "= :" + n).collect(Collectors.joining(", ")))
                .append(" where id = :id").toString();

        this.deleteSql = new StringBuilder().append("delete from ").append(table).append(" where id = :id").toString();

        this.countSql = new StringBuilder().append("select count(*) from ").append(table).toString();
    }

    @Override
    public long count() {
        return jdbc.queryForObject(countSql, Map.of(), Long.class);
    }

    @Override
    public void create(T elem) {
        jdbc.update(insertSql, mapper.toMap(elem));
    }

    @Override
    public void update(T elem) {
        jdbc.update(updateSql, mapper.toMap(elem));
    }

    @Override
    public void delete(long id) {
        jdbc.update(deleteSql, Map.of("id", id));
    }

    @Override
    public T readById(long id) {
        return jdbc.queryForObject(select1Sql, Map.of("id", id), mapper);
    }

    protected T readOneByCondition(String whereClause, Map<String, Object> params) {
        return jdbc.queryForObject(String.format(selectWhereSql, whereClause), params, mapper);
    }

    @Override
    public List<T> readAll() {
        return jdbc.query(selectSql, mapper);
    }

    protected interface Mapper<T> extends RowMapper<T> {
        /// Должен вернуть словать "имя столбца": значение
        Map<String, Object> toMap(T object);
    }
}
