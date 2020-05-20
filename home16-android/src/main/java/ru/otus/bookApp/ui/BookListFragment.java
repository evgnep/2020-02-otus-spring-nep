package ru.otus.bookApp.ui;

import android.os.Bundle;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import ru.otus.bookApp.Main;
import ru.otus.bookApp.R;
import ru.otus.bookApp.model.Storage;
import ru.otus.bookApp.ui.helper.BaseListFragment;
import ru.otus.home7.rest.dto.BookDto;

public class BookListFragment extends BaseListFragment<BookDto> {
    public BookListFragment() {
        super(R.layout.book_list_content);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

    }

    @Override
    public void onListItemClick(@NonNull ListView l, @NonNull View v, int position, long id) {
        ((MainActivity) getActivity()).showBook(id == -1 ? -1 : position);
    }

    @Override
    protected Storage<BookDto> getStorage() {
        return Main.get(this).getBookStorage();
    }

    @Override
    protected void dataToView(BookDto elem, View view) {
        TextView id = view.findViewById(R.id.id);
        TextView name = view.findViewById(R.id.book);
        TextView genre = view.findViewById(R.id.genre);
        TextView author = view.findViewById(R.id.author);

        if (elem != null) {
            id.setText(Long.toString(elem.getId()));
            name.setText(elem.getName());
            genre.setText(elem.getGenre().getName());
            author.setText(elem.getAuthor().getName());
        } else {
            id.setText("");
            genre.setText("");
            author.setText("");
            name.setText("<Новая книга>");
        }
    }
}
