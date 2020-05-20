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

import java.util.stream.Collectors;

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

        int pos = getArguments().getInt("pos");

        BookStorage storage = Main.get(this).getBookStorage();
        book = pos == -1 ? new BookDto() : storage.get(pos);

        vId.setText(pos == -1 ? "-" : Long.toString(book.getId()));
        eName.setText(book.getName());
        eDescription.setText(book.getDescription());


        SpinnerAdapter adapter = new ArrayAdapter<AuthorWrapper>(container.getContext(), android.R.layout.simple_spinner_dropdown_item,
                Main.get(this).getAuthorStorage().getItems().stream().map(AuthorWrapper::new).collect(Collectors.toList())) {
            @Override
            public long getItemId(int position) {
                return getItem(position).getId();
            }
        };
        sAuthor.setAdapter(adapter);

        sAuthor.setSele

        setupButtonsAndStorage(view, storage, pos);

        return view;
    }

    private static class AuthorWrapper {
        final AuthorDto author;

        AuthorWrapper(AuthorDto author) {
            this.author = author;
        }

        @Override
        public String toString() {
            return author.getName();
        }

        public long getId() {
            return author.getId();
        }
    }
}

