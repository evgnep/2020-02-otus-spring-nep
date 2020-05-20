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
import ru.otus.bookApp.model.AuthorStorage;
import ru.otus.bookApp.ui.helper.BaseDialogFragment;
import ru.otus.home7.rest.dto.AuthorDto;

public class AuthorFragment extends BaseDialogFragment<AuthorDto> {

    private EditText eName;
    private AuthorDto author;

    public static AuthorFragment createInstance(int pos) {
        AuthorFragment f = new AuthorFragment();
        Bundle b = new Bundle();
        b.putInt("pos", pos);
        f.setArguments(b);
        return f;
    }

    @Override
    protected AuthorDto viewToElem() {
        author.setName(eName.getText().toString());
        return author;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_simple_detail, container, false);

        TextView vId = view.findViewById(R.id.id);
        eName = view.findViewById(R.id.name);

        int pos = getArguments().getInt("pos");

        AuthorStorage storage = Main.get(this).getAuthorStorage();
        author = pos == -1 ? new AuthorDto().setName("") : storage.get(pos);

        vId.setText(pos == -1 ? "-" : Long.toString(author.getId()));
        eName.setText(author.getName());

        setupButtonsAndStorage(view, storage, pos);

        return view;
    }
}

