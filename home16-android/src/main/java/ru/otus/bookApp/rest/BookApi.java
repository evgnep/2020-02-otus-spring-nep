package ru.otus.bookApp.rest;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.*;
import ru.otus.home7.rest.dto.BookDto;

import java.util.List;

interface BookApi {
    @GET("/rest/book")
    Call<List<BookDto>> getAll();

    @GET("/rest/book/{id}")
    Call<BookDto> get(@Path("id") long id);

    @POST("/rest/book")
    Call<BookDto> save(@Body BookDto elem);

    @DELETE("/rest/book/{id}")
    Call<ResponseBody> delete(@Path("id") long id);
}

class BookApiImpl implements Api<BookDto> {
    private final BookApi api;

    public BookApiImpl(BookApi api) {
        this.api = api;
    }

    @Override
    public Call<List<BookDto>> getAll() {
        return api.getAll();
    }

    @Override
    public Call<BookDto> get(long id) {
        return api.get(id);
    }

    @Override
    public Call<BookDto> save(BookDto elem) {
        return api.save(elem);
    }

    @Override
    public Call<ResponseBody> delete(long id) {
        return api.delete(id);
    }
}
