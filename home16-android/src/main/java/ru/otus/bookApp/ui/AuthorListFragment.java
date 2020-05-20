package ru.otus.bookApp.ui;

import android.view.View;
import android.widget.ListView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import ru.otus.bookApp.Main;
import ru.otus.bookApp.R;
import ru.otus.bookApp.model.Storage;
import ru.otus.bookApp.ui.helper.BaseListFragment;
import ru.otus.home7.rest.dto.AuthorDto;

public class AuthorListFragment extends BaseListFragment<AuthorDto> {

    public AuthorListFragment() {
        super(R.layout.item_list_content);
    }

    @Override
    public void onListItemClick(@NonNull ListView l, @NonNull View v, int position, long id) {
        ((MainActivity) getActivity()).showAuthor(id == -1 ? -1 : position);
    }

    @Override
    protected Storage<AuthorDto> getStorage() {
        return Main.get(this).getAuthorStorage();
    }

    @Override
    protected void dataToView(AuthorDto elem, View view) {
        TextView id = view.findViewById(R.id.id);
        TextView name = view.findViewById(R.id.content);

        if (elem != null) {
            id.setText(Long.toString(elem.getId()));
            name.setText(elem.getName());
        } else {
            id.setText("");
            name.setText("<Новый автор>");
        }
    }
}
