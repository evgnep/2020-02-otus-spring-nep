package ru.otus.bookApp.ui;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import ru.otus.bookApp.R;
import ru.otus.bookApp.rest.NetworkService;
import ru.otus.home7.rest.dto.AuthorDto;

import java.util.List;

public class AuthorListActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_author_list);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        toolbar.setTitle(getTitle());

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        NetworkService.INSTANCE.getAuthorApi().getAuthors()
                .enqueue(new Callback<List<AuthorDto>>() {
                    @Override
                    public void onResponse(@NonNull Call<List<AuthorDto>> call, @NonNull Response<List<AuthorDto>> response) {
                        RecyclerView recyclerView = (RecyclerView)findViewById(R.id.recyclerView);
                        //recyclerView.setLayoutManager(new LinearLayoutManager(AuthorListActivity.this));
                        recyclerView.setAdapter(new SimpleItemRecyclerViewAdapter( response.body()));

                    }

                    @Override
                    public void onFailure(@NonNull Call<List<AuthorDto>> call, @NonNull Throwable t) {

                        // textView.append("Error occurred while getting request!");
                        t.printStackTrace();
                    }
                });
    }

    public class SimpleItemRecyclerViewAdapter extends RecyclerView.Adapter<ViewHolder> {

        private final List<AuthorDto> authors;

//        private final View.OnClickListener mOnClickListener = new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                DummyContent.DummyItem item = (DummyContent.DummyItem) view.getTag();
//
//                Context context = view.getContext();
//                Intent intent = new Intent(context, ItemDetailActivity.class);
//                intent.putExtra(ItemDetailFragment.ARG_ITEM_ID, item.id);
//
//                context.startActivity(intent);
//            }
//        };

        SimpleItemRecyclerViewAdapter(List<AuthorDto> authors) {
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
            holder.mIdView.setText(Long.toString(authors.get(position).getId()));
            holder.mContentView.setText(authors.get(position).getName());

            holder.itemView.setTag(authors.get(position));
            //holder.itemView.setOnClickListener(mOnClickListener);
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
