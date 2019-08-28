package net.emrekalkan.todolistapp.ui.todo.filterTodo;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.databinding.ObservableField;

import net.emrekalkan.todolistapp.R;
import net.emrekalkan.todolistapp.core.BaseViewModel;
import net.emrekalkan.todolistapp.utils.Constants;

public class FilterTodoViewModel extends BaseViewModel {
    FilterTodoViewModel(@NonNull Application application) {
        super(application);
    }

    ObservableField<String> filterName = new ObservableField<>();
    public ObservableField<String> getFilterName() {
        return filterName;
    }

    public int getViewType(String filterName) {
        final String completed = getApplication().getString(R.string.completed);
        final String active = getApplication().getString(R.string.active);
        final String expired = getApplication().getString(R.string.expired);

        if (completed.equals(filterName))
            return Constants.TodoItemViewType.COMPLETED;

        else if (active.equals(filterName))
            return Constants.TodoItemViewType.ACTIVE;

        else if (expired.equals(filterName))
            return Constants.TodoItemViewType.EXPIRED;
        else
            return Constants.TodoItemViewType.ALL;
    }

}
