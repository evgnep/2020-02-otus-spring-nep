package ru.otus.bookApp.rest;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import ru.otus.home7.rest.dto.AuthorDto;

import java.util.List;

public interface AuthorApi {
    @GET("/rest/author")
    Call<List<AuthorDto>> getAuthors();

    @GET("/rest/author/{id}")
    Call<AuthorDto> getAuthor(@Path("id") long id);
}
