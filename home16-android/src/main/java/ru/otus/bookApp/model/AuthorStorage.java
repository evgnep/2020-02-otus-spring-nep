package ru.otus.bookApp.model;

import ru.otus.bookApp.rest.NetworkService;
import ru.otus.home7.rest.dto.AuthorDto;

public class AuthorStorage extends Storage<AuthorDto> {
    public static final AuthorStorage INSTANCE = new AuthorStorage();

    private AuthorStorage() {
        super(NetworkService.INSTANCE.getAuthorApi());
    }

    @Override
    public long getId(AuthorDto elem) {
        return elem.getId();
    }
}
