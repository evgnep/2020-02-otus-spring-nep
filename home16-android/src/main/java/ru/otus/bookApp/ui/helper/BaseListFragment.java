package ru.otus.bookApp.ui.helper;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import androidx.annotation.Nullable;
import androidx.fragment.app.ListFragment;
import com.google.android.material.snackbar.Snackbar;
import ru.otus.bookApp.model.Storage;

import java.util.ArrayList;
import java.util.List;

/**
 * Хелпер для реализации фрагментов-списков на основе хранилища
 */
public abstract class BaseListFragment<T> extends ListFragment {
    protected final int listItemLayout;
    protected Storage<T> storage;

    public BaseListFragment(int listItemLayout) {
        this.listItemLayout = listItemLayout;
    }

    protected abstract Storage<T> getStorage();

    protected abstract void dataToView(T elem, View view);

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        storage = getStorage();
        storage.observeState(this, state -> {
            setListShown(!state.isInProgress());
            if (state.hasError())
                Snackbar.make(getView(), state.getAndResetError(), Snackbar.LENGTH_LONG).show();
        });
        storage.observeItems(this, list -> {
            if (getListAdapter() != null)
                ((BaseListAdapter) getListAdapter()).notifyDataSetChanged();
            else
                setListAdapter(new BaseListAdapter(list, getContext()));
        });
    }

    private class BaseListAdapter extends BaseAdapter {
        private final List<T> list;
        private final LayoutInflater inflater;

        public BaseListAdapter(List<T> list, Context context) {

            this.list = list == null ? new ArrayList<>() : list;
            this.inflater = LayoutInflater.from(context);
        }

        @Override
        public int getCount() {
            return list.size() + 1;
        }

        @Override
        public Object getItem(int position) {
            return position == list.size() ? null : list.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position == list.size() ? -1 : storage.getId(list.get(position));
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            if (convertView == null)
                convertView = inflater.inflate(listItemLayout, parent, false);

            dataToView(position < list.size() ? list.get(position) : null, convertView);

            return convertView;
        }
    }
}
