package ru.otus.bookApp.model;

import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Observer;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import ru.otus.bookApp.rest.Api;

import java.util.List;
import java.util.Objects;
import java.util.function.Consumer;

/**
 * Базовый класс хранилища (кэша)
 * Элементы однократно запрашиваются через Api, а затем обновляются через Api и локально
 */
public abstract class Storage<T> {
    private final Api<T> api;
    private final MutableLiveData<List<T>> items = new MutableLiveData<>();
    private final MutableLiveData<State> state = new MutableLiveData<>(new State());

    public Storage(Api<T> api) {
        this.api = api;

        api.getAll().enqueue(new CallbackAdapter<>(items::setValue));
    }

    private void checkNoOp() {
        if (state.getValue().isInProgress())
            throw new RuntimeException("Операция выполняется");
    }

    private void onStart() {
        state.getValue().setInProgress(true);
        state.setValue(state.getValue());
    }

    private void onComplete(String error) {
        state.getValue().setInProgress(false);
        state.getValue().setError(error);
        state.setValue(state.getValue());
    }

    public abstract long getId(T elem);

    public List<T> getItems() {
        return items.getValue();
    }

    public int size() {
        return Objects.requireNonNull(items.getValue()).size();
    }

    public T get(int i) {
        return Objects.requireNonNull(items.getValue()).get(i);
    }

    public void set(int i, T elem) {
        if (i == -1) {
            add(elem);
            return;
        }

        checkNoOp();

        api.save(elem).enqueue(new CallbackAdapter<>(x -> {
            items.getValue().set(i, elem);
            items.setValue(items.getValue());
        }));
    }

    public void delete(int index) {
        checkNoOp();
        long id = getId(items.getValue().get(index));
        api.delete(id).enqueue(new CallbackAdapter<>(x -> {
            items.getValue().remove(index);
            items.setValue(items.getValue());
        }));
    }

    public void add(T elem) {
        checkNoOp();
        api.save(elem).enqueue(new CallbackAdapter<>(x -> {
            items.getValue().add(x);
            items.setValue(items.getValue());
        }));
    }

    public String getError() {
        return state.getValue().getError();
    }

    public boolean hasError() {
        return !getError().isEmpty();
    }

    public boolean isInProgress() {
        return state.getValue().isInProgress();
    }

    public void observeState(LifecycleOwner owner, Observer<? super State> observer) {
        state.observe(owner, observer);
    }

    public void observeItems(LifecycleOwner owner, Observer<? super List<T>> observer) {
        items.observe(owner, observer);
    }

    private class CallbackAdapter<T> implements Callback<T> {
        Consumer<T> consumer;

        CallbackAdapter(Consumer<T> consumer) {
            this.consumer = consumer;
            onStart();
        }

        @Override
        public void onResponse(Call<T> call, Response<T> response) {
            onComplete("");
            if (consumer != null)
                consumer.accept(response.body());
        }

        @Override
        public void onFailure(Call<T> call, Throwable t) {
            onComplete(t.getMessage());
        }
    }
}
