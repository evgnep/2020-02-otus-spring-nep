package ru.otus.bookApp.ui;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.RecyclerView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import ru.otus.bookApp.R;
import ru.otus.bookApp.model.AuthorStorage;
import ru.otus.home7.rest.dto.AuthorDto;

public class AuthorListActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        toolbar.setTitle(getTitle());

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(view -> Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                .setAction("Action", null).show());

        AuthorStorage.INSTANCE.init(s -> {
            if (!s.isEmpty())
                Snackbar.make(findViewById(android.R.id.content), "Troubles: " + s, Snackbar.LENGTH_LONG)
                        .show();
            else {
                RecyclerView recyclerView = (RecyclerView) findViewById(R.id.recyclerView);
                recyclerView.setAdapter(new SimpleItemRecyclerViewAdapter(AuthorStorage.INSTANCE));
            }
        });
    }

    public static class SimpleItemRecyclerViewAdapter extends RecyclerView.Adapter<ViewHolder> {

        private final AuthorStorage authors;

        private final View.OnClickListener mOnClickListener = new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int pos = (int) view.getTag();

                Context context = view.getContext();
                Intent intent = new Intent(context, AuthorActivity.class);
                intent.putExtra("POSITION", pos);

                context.startActivity(intent);
            }
        };

        SimpleItemRecyclerViewAdapter(AuthorStorage authors) {
            this.authors = authors;
        }

        @Override
        public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.item_list_content, parent, false);
            return new ViewHolder(view);
        }

        @Override
        public void onBindViewHolder(final ViewHolder holder, int position) {
            AuthorDto a = authors.get(position);
            holder.mIdView.setText(Long.toString(a.getId()));
            holder.mContentView.setText(a.getName());

            holder.itemView.setTag(position);
            holder.itemView.setOnClickListener(mOnClickListener);
        }

        @Override
        public int getItemCount() {
            return authors.size();
        }
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        final TextView mIdView;
        final TextView mContentView;

        ViewHolder(View view) {
            super(view);
            mIdView = (TextView) view.findViewById(R.id.id_text);
            mContentView = (TextView) view.findViewById(R.id.content);
        }
    }
}
