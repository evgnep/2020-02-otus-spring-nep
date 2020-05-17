package ru.otus.bookApp.ui;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import com.google.android.material.snackbar.Snackbar;
import ru.otus.bookApp.R;
import ru.otus.bookApp.model.AuthorStorage;
import ru.otus.home7.rest.dto.AuthorDto;

public class AuthorActivity extends AppCompatActivity {

    private AuthorDto author;
    private int pos;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_simple_detail);

        pos = getIntent().getIntExtra("POSITION", -1);

        author = pos == -1 ? new AuthorDto() : AuthorStorage.INSTANCE.get(pos);

        TextView vId = findViewById(R.id.id);
        vId.setText(Long.toString(author.getId()));

        EditText eName = findViewById(R.id.name);
        eName.setText(author.getName());

        ((Button) findViewById(R.id.cancel)).setOnClickListener(v -> {
            finish();
        });

        Button buttonDelete = findViewById(R.id.delete);
        if (pos == -1)
            buttonDelete.setVisibility(View.INVISIBLE);
        else
            buttonDelete.setOnClickListener(v -> {
                AuthorStorage.INSTANCE.delete(pos, error -> {
                    if (error.isEmpty())
                        finish();
                    else
                        Snackbar.make(findViewById(android.R.id.content), "Troubles: " + error, Snackbar.LENGTH_LONG)
                                .show();
                });
            });

//        NetworkService.INSTANCE.getAuthorApi().getAuthors()
//                .enqueue(new Callback<List<AuthorDto>>() {
//                    @Override
//                    public void onResponse(@NonNull Call<List<AuthorDto>> call, @NonNull Response<List<AuthorDto>> response) {
//                        RecyclerView recyclerView = (RecyclerView)findViewById(R.id.recyclerView);
//                        recyclerView.setAdapter(new SimpleItemRecyclerViewAdapter( response.body()));
//
//                    }
//
//                    @Override
//                    public void onFailure(@NonNull Call<List<AuthorDto>> call, @NonNull Throwable t) {
//                        Snackbar.make(findViewById(android.R.id.content), "Troubles: " + t.getMessage(), Snackbar.LENGTH_LONG)
//                                .show();
//                    }
//                });
    }


}
