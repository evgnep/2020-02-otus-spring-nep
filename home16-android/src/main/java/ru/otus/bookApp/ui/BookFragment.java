package ru.otus.bookApp.ui;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.*;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import ru.otus.bookApp.Main;
import ru.otus.bookApp.R;
import ru.otus.bookApp.model.BookStorage;
import ru.otus.bookApp.ui.helper.BaseDialogFragment;
import ru.otus.home7.rest.dto.AuthorDto;
import ru.otus.home7.rest.dto.BookDto;
import ru.otus.home7.rest.dto.GenreDto;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;

public class BookFragment extends BaseDialogFragment<BookDto> {

    private EditText eName, eDescription;
    private Spinner sAuthor, sGenre;
    private BookDto book;


    public static BookFragment createInstance(int pos) {
        BookFragment f = new BookFragment();
        Bundle b = new Bundle();
        b.putInt("pos", pos);
        f.setArguments(b);
        return f;
    }

    @Override
    protected BookDto viewToElem() {
        book.setName(eName.getText().toString());
        book.setDescription(eDescription.getText().toString());
        book.getAuthor().setId(sAuthor.getSelectedItemId());
        book.getGenre().setId(sGenre.getSelectedItemId());
        return book;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.book_detail, container, false);

        TextView vId = view.findViewById(R.id.id);
        eName = view.findViewById(R.id.name);
        eDescription = view.findViewById(R.id.description);
        sAuthor = view.findViewById(R.id.author);
        sGenre = view.findViewById(R.id.genre);

        int pos = getArguments().getInt("pos");

        BookStorage storage = Main.get(this).getBookStorage();
        book = pos == -1 ? new BookDto().setAuthor(new AuthorDto()).setGenre(new GenreDto()) : storage.get(pos);

        vId.setText(pos == -1 ? "-" : Long.toString(book.getId()));
        eName.setText(book.getName());
        eDescription.setText(book.getDescription());

        populateSpinner(sAuthor, Main.get(this).getAuthorStorage().getItems(), IdName::new, book.getAuthor().getId());
        populateSpinner(sGenre, Main.get(this).getGenreStorage().getItems(), IdName::new, book.getGenre().getId());

        setupButtonsAndStorage(view, storage, pos);

        return view;
    }

    private <T> void populateSpinner(Spinner spinner, List<T> items, Function<T, IdName> converter, long current) {
        List<IdName> idNames = new ArrayList<>();

        int selected = -1;
        for (T elem : items) {
            IdName idName = converter.apply(elem);
            if (idName.id == current)
                selected = idNames.size();
            idNames.add(idName);
        }

        SpinnerAdapter adapter = new ArrayAdapter<IdName>(getContext(), android.R.layout.simple_spinner_dropdown_item, idNames) {
            @Override
            public long getItemId(int position) {
                return getItem(position).id;
            }
        };
        spinner.setAdapter(adapter);
        if (selected != -1)
            spinner.setSelection(selected);
    }

    private static class IdName {
        final long id;
        final String name;

        IdName(AuthorDto author) {
            id = author.getId();
            name = author.getName();
        }

        IdName(GenreDto genre) {
            id = genre.getId();
            name = genre.getName();
        }

        @Override
        public String toString() {
            return name;
        }
    }
}

