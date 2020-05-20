package ru.otus.bookApp.rest;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import ru.otus.home7.rest.dto.AuthorDto;
import ru.otus.home7.rest.dto.BookDto;
import ru.otus.home7.rest.dto.GenreDto;

/**
 * Точка доступа ко всем Api для сервера
 */
public class NetworkService {
    private static final String BASE_URL = "http://192.168.1.10:8080";
    private final Retrofit mRetrofit;
    private final AuthorApiImpl authorApi;
    private final GenreApiImpl genreApi;
    private final BookApiImpl bookApi;

    public NetworkService() {
        mRetrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        authorApi = new AuthorApiImpl(mRetrofit.create(AuthorApi.class));
        genreApi = new GenreApiImpl(mRetrofit.create(GenreApi.class));
        bookApi = new BookApiImpl(mRetrofit.create(BookApi.class));
    }

    public Api<AuthorDto> getAuthorApi() {
        return authorApi;
    }

    public Api<GenreDto> getGenreApi() {
        return genreApi;
    }

    public Api<BookDto> getBookApi() {
        return bookApi;
    }
}
