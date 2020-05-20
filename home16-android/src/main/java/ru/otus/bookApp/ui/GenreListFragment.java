package ru.otus.bookApp.ui;

import android.view.View;
import android.widget.ListView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import ru.otus.bookApp.Main;
import ru.otus.bookApp.R;
import ru.otus.bookApp.model.Storage;
import ru.otus.bookApp.ui.helper.BaseListFragment;
import ru.otus.home7.rest.dto.GenreDto;

public class GenreListFragment extends BaseListFragment<GenreDto> {

    public GenreListFragment() {
        super(R.layout.item_list_content);
    }

    @Override
    protected Storage<GenreDto> getStorage() {
        return Main.get(this).getGenreStorage();
    }

    @Override
    protected void dataToView(GenreDto elem, View view) {
        TextView id = view.findViewById(R.id.id);
        TextView name = view.findViewById(R.id.content);

        if (elem != null) {
            id.setText(Long.toString(elem.getId()));
            name.setText(elem.getName());
        } else {
            id.setText("");
            name.setText("<Новый жанр>");
        }
    }

    @Override
    public void onListItemClick(@NonNull ListView l, @NonNull View v, int position, long id) {
        ((MainActivity) getActivity()).showGenre(id == -1 ? -1 : position);
    }
}
