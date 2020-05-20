package ru.otus.bookApp.ui;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import ru.otus.bookApp.Main;
import ru.otus.bookApp.R;
import ru.otus.bookApp.model.GenreStorage;
import ru.otus.bookApp.ui.helper.BaseDialogFragment;
import ru.otus.home7.rest.dto.GenreDto;

public class GenreFragment extends BaseDialogFragment<GenreDto> {

    private EditText eName;
    private GenreDto genre;

    public static GenreFragment createInstance(int pos) {
        GenreFragment f = new GenreFragment();
        Bundle b = new Bundle();
        b.putInt("pos", pos);
        f.setArguments(b);
        return f;
    }

    @Override
    protected GenreDto viewToElem() {
        genre.setName(eName.getText().toString());
        return genre;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_simple_detail, container, false);

        TextView vId = view.findViewById(R.id.id);
        eName = view.findViewById(R.id.name);

        int pos = getArguments().getInt("pos");

        GenreStorage storage = Main.get(this).getGenreStorage();
        genre = pos == -1 ? new GenreDto().setName("") : storage.get(pos);

        vId.setText(pos == -1 ? "-" : Long.toString(genre.getId()));
        eName.setText(genre.getName());

        setupButtonsAndStorage(view, storage, pos);

        return view;
    }
}

