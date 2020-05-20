package ru.otus.bookApp.rest;

import okhttp3.ResponseBody;
import retrofit2.Call;

import java.util.List;

/**
 * Интерфейс для работы с сервером
 * T - XxxDto
 */
public interface Api<T> {
    Call<List<T>> getAll();

    Call<T> get(long id);

    Call<T> save(T elem);

    Call<ResponseBody> delete(long id);
}
