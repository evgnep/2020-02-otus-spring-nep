package ru.otus.bookApp.ui.helper;

import android.view.View;
import android.widget.Button;
import androidx.fragment.app.Fragment;
import com.google.android.material.snackbar.Snackbar;
import ru.otus.bookApp.R;
import ru.otus.bookApp.model.Storage;

/**
 * Вспомогательный класс для фрагмента редактирования элемента
 */
public abstract class BaseDialogFragment<T> extends Fragment {
    protected boolean closeOnOk;

    protected abstract T viewToElem();

    protected void setupButtonsAndStorage(View view, Storage<T> storage, int pos) {
        view.<Button>findViewById(R.id.cancel).setOnClickListener(v -> getFragmentManager().popBackStack());

        Button btDelete = view.findViewById(R.id.delete);
        if (pos == -1)
            btDelete.setVisibility(View.INVISIBLE);
        else
            btDelete.setOnClickListener(v -> {
                closeOnOk = true;
                storage.delete(pos);
            });

        view.<Button>findViewById(R.id.ok).setOnClickListener(v -> {
            closeOnOk = true;
            storage.set(pos, viewToElem());
        });

        storage.observeState(this, state -> {
            if (state.hasError())
                Snackbar.make(getView(), state.getAndResetError(), Snackbar.LENGTH_LONG).show();
            else if (closeOnOk)
                getFragmentManager().popBackStack();
        });
    }
}
