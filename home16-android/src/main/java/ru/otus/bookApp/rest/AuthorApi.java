package ru.otus.bookApp.rest;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.*;
import ru.otus.home7.rest.dto.AuthorDto;

import java.util.List;

interface AuthorApi {
    @GET("/rest/author")
    Call<List<AuthorDto>> getAll();

    @GET("/rest/author/{id}")
    Call<AuthorDto> get(@Path("id") long id);

    @POST("/rest/author")
    Call<AuthorDto> save(@Body AuthorDto elem);

    @DELETE("/rest/author/{id}")
    Call<ResponseBody> delete(@Path("id") long id);
}

class AuthorApiImpl implements Api<AuthorDto> {
    private final AuthorApi api;

    public AuthorApiImpl(AuthorApi api) {
        this.api = api;
    }

    @Override
    public Call<List<AuthorDto>> getAll() {
        return api.getAll();
    }

    @Override
    public Call<AuthorDto> get(long id) {
        return api.get(id);
    }

    @Override
    public Call<AuthorDto> save(AuthorDto elem) {
        return api.save(elem);
    }

    @Override
    public Call<ResponseBody> delete(long id) {
        return api.delete(id);
    }
}
