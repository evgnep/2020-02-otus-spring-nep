package ru.otus.bookApp.model;

import ru.otus.bookApp.rest.Api;
import ru.otus.home7.rest.dto.GenreDto;

public class GenreStorage extends Storage<GenreDto> {
    public GenreStorage(Api<GenreDto> api) {
        super(api);
    }

    @Override
    public long getId(GenreDto elem) {
        return elem.getId();
    }
}
