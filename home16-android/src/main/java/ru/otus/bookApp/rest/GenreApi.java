package ru.otus.bookApp.rest;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.*;
import ru.otus.home7.rest.dto.GenreDto;

import java.util.List;

interface GenreApi {
    @GET("/rest/genre")
    Call<List<GenreDto>> getAll();

    @GET("/rest/genre/{id}")
    Call<GenreDto> get(@Path("id") long id);

    @POST("/rest/genre")
    Call<GenreDto> save(@Body GenreDto elem);

    @DELETE("/rest/genre/{id}")
    Call<ResponseBody> delete(@Path("id") long id);
}

class GenreApiImpl implements Api<GenreDto> {
    private final GenreApi api;

    public GenreApiImpl(GenreApi api) {
        this.api = api;
    }

    @Override
    public Call<List<GenreDto>> getAll() {
        return api.getAll();
    }

    @Override
    public Call<GenreDto> get(long id) {
        return api.get(id);
    }

    @Override
    public Call<GenreDto> save(GenreDto elem) {
        return api.save(elem);
    }

    @Override
    public Call<ResponseBody> delete(long id) {
        return api.delete(id);
    }
}
