package ru.otus.bookApp.model;

import ru.otus.bookApp.rest.Api;
import ru.otus.home7.rest.dto.BookDto;

public class BookStorage extends Storage<BookDto> {
    public BookStorage(Api<BookDto> api) {
        super(api);
    }

    @Override
    public long getId(BookDto elem) {
        return elem.getId();
    }
}
