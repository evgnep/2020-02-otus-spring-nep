package ru.otus.bookApp.model;

import ru.otus.bookApp.rest.Api;
import ru.otus.home7.rest.dto.AuthorDto;

public class AuthorStorage extends Storage<AuthorDto> {
    public AuthorStorage(Api<AuthorDto> api) {
        super(api);
    }

    @Override
    public long getId(AuthorDto elem) {
        return elem.getId();
    }
}
