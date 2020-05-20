package ru.otus.bookApp;

import android.app.Activity;
import android.app.Application;
import androidx.fragment.app.Fragment;
import ru.otus.bookApp.model.AuthorStorage;
import ru.otus.bookApp.model.BookStorage;
import ru.otus.bookApp.model.GenreStorage;
import ru.otus.bookApp.rest.NetworkService;

public class Main extends Application {
    private NetworkService service;
    private AuthorStorage authorStorage;
    private GenreStorage genreStorage;
    private BookStorage bookStorage;

    public static Main get(Activity activity) {
        return (Main) activity.getApplication();
    }

    public static Main get(Fragment fragment) {
        return (Main) fragment.getActivity().getApplication();
    }

    @Override
    public void onCreate() {
        super.onCreate();

        service = new NetworkService();
        authorStorage = new AuthorStorage(service.getAuthorApi());
        genreStorage = new GenreStorage(service.getGenreApi());
        bookStorage = new BookStorage(service.getBookApi());
    }

    public AuthorStorage getAuthorStorage() {
        return authorStorage;
    }

    public GenreStorage getGenreStorage() {
        return genreStorage;
    }

    public BookStorage getBookStorage() {
        return bookStorage;
    }
}
