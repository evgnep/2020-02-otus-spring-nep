package ru.otus.bookApp.ui;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import ru.otus.bookApp.R;

public class MainActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        showBooks();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menuAuthor:
                showAuthors();
                break;
            case R.id.menuBook:
                showBooks();
                break;
            case R.id.menuGenre:
                showGenres();
                break;
            default:
                return false;
        }
        return true;
    }

    public void showBooks() {
        FragmentManager manager = getSupportFragmentManager();
        Fragment fragment = manager.findFragmentByTag("Books");
        if (fragment == null)
            fragment = new BookListFragment();
        manager.beginTransaction()
                .replace(R.id.frameLayout, fragment, "Books")
                .addToBackStack(null).commit();
    }

    public void showAuthors() {
        FragmentManager manager = getSupportFragmentManager();
        Fragment fragment = manager.findFragmentByTag("Authors");
        if (fragment == null)
            fragment = new AuthorListFragment();
        manager.beginTransaction()
                .replace(R.id.frameLayout, fragment, "Authors")
                .addToBackStack(null).commit();
    }

    public void showAuthor(int position) {
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.frameLayout, AuthorFragment.createInstance(position))
                .addToBackStack(null).commit();
    }

    public void showGenres() {
        FragmentManager manager = getSupportFragmentManager();
        Fragment fragment = manager.findFragmentByTag("Genres");
        if (fragment == null)
            fragment = new GenreListFragment();
        manager.beginTransaction()
                .replace(R.id.frameLayout, fragment, "Genres")
                .addToBackStack(null).commit();
    }

    public void showGenre(int position) {
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.frameLayout, GenreFragment.createInstance(position))
                .addToBackStack(null).commit();
    }

    public void showBook(int position) {
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.frameLayout, BookFragment.createInstance(position))
                .addToBackStack(null).commit();
    }
}
