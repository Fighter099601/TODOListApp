package net.emrekalkan.todolistapp.ui.todo.orderTodo;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.databinding.ObservableField;

import net.emrekalkan.todolistapp.R;
import net.emrekalkan.todolistapp.core.BaseViewModel;
import net.emrekalkan.todolistapp.utils.Constants;

public class OrderTodoViewModel extends BaseViewModel {
    OrderTodoViewModel(@NonNull Application application) {
        super(application);
    }

    ObservableField<String> orderName = new ObservableField<>();
    public ObservableField<String> getOrderName() {
        return orderName;
    }

    public int getViewType(String filterName) {
        final String deadline = getApplication().getString(R.string.deadline);
        final String status = getApplication().getString(R.string.status);
        final String name = getApplication().getString(R.string.name);

        if (deadline.equals(filterName))
            return Constants.TodoItemViewType.DEADLINE;

        else if (status.equals(filterName))
            return Constants.TodoItemViewType.STATUS;

        else if (name.equals(filterName))
            return Constants.TodoItemViewType.NAME;
        else
            return Constants.TodoItemViewType.CREATE_DATE;
    }

}
