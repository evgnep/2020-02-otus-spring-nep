package ru.otus.bookApp.model;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import ru.otus.bookApp.rest.Api;

import java.util.List;
import java.util.function.Consumer;

public abstract class Storage<T> {
    private final Api<T> api;
    private List<T> items;
    private String error = "";

    public Storage(Api<T> api) {
        this.api = api;
    }

    public abstract long getId(T elem);

    public void init(Consumer<String> onComplete) {
        api.getAll().enqueue(new Callback<List<T>>() {
            @Override
            public void onResponse(Call<List<T>> call, Response<List<T>> response) {
                items = response.body();
                error = "";
                onComplete.accept(error);
            }

            @Override
            public void onFailure(Call<List<T>> call, Throwable t) {
                error = t.getMessage();
                onComplete.accept(error);
            }
        });
    }

    public int size() {
        return items == null ? 0 : items.size();
    }

    public T get(int i) {
        return items.get(i);
    }

    public Call<T> set(int i, T elem) {
        items.set(i, elem);
        Call<T> call = api.save(elem);
        return call;
    }

    public void delete(int index, Consumer<String> onComplete) {
        api.delete(getId(items.get(index))).enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                items.remove(index);
                onComplete.accept("");
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                onComplete.accept(t.getMessage());
            }
        });
    }

    public Call<T> add(T elem) {
        items.add(elem);
        Call<T> call = api.save(elem);
        return call;
    }

    public String getError() {
        return error;
    }

    boolean hasError() {
        return error.isEmpty();
    }
}
