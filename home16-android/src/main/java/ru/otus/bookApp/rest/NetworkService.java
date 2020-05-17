package ru.otus.bookApp.rest;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import ru.otus.home7.rest.dto.AuthorDto;

public class NetworkService {
    public final static NetworkService INSTANCE = new NetworkService();

    private static final String BASE_URL = "http://192.168.1.10:8080";
    private final Retrofit mRetrofit;
    private final AuthorApiImpl authorApi;

    private NetworkService() {
        mRetrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        authorApi = new AuthorApiImpl(mRetrofit.create(AuthorApi.class));
    }

    public Api<AuthorDto> getAuthorApi() {
        return authorApi;
    }
}
