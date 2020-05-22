package ru.otus.home20.repository;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.ReactiveMongoTemplate;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import reactor.core.publisher.Mono;

import java.util.function.BiConsumer;
import java.util.function.Function;

@Component
public class SaveService {
    private final ReactiveMongoTemplate template;

    public SaveService(ReactiveMongoTemplate template) {
        this.template = template;
    }

    /**
     * У меня числовой Id
     * Данный метод выполняет обновление или вставку.
     * При вставке он находит максимальный Id и меняет Id элемента на макс+1
     *
     * @param entity что сохраняем
     * @param getId  метод получения Id
     * @param setId  метод установи Id
     */
    @Transactional
    public <T> Mono<T> save(Mono<T> entity, Function<T, Long> getId, BiConsumer<T, Long> setId) {
        return entity.flatMap(e -> {
                    if (getId.apply(e) != 0)
                        return template.save(e);

                    var query = new Query();
                    query.fields().include("_id");
                    query.with(PageRequest.of(0, 1, Sort.Direction.DESC, "_id"));

                    return template.findOne(query, (Class<T>) e.getClass())
                            .map(getId)
                            .defaultIfEmpty(0L)
                            .map(max -> {
                                setId.accept(e, max + 1);
                                return e;
                            })
                            .flatMap(template::save);
                }
        );
    }
}
