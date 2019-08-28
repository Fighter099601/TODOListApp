package net.emrekalkan.todolistapp.ui.addTodo;

import android.app.DatePickerDialog;
import android.view.Menu;
import android.view.MenuInflater;

import androidx.lifecycle.ViewModelProviders;

import net.emrekalkan.todolistapp.R;
import net.emrekalkan.todolistapp.core.BaseFragment;
import net.emrekalkan.todolistapp.databinding.FragmentTodoItemAddBinding;
import net.emrekalkan.todolistapp.db.entity.TodoEntity;
import net.emrekalkan.todolistapp.utils.Constants;

import java.util.Calendar;

public class TodoItemAddFragment extends BaseFragment<TodoItemAddViewModel, FragmentTodoItemAddBinding> implements TodoItemAddInterface {

    @Override
    protected int getLayoutRes() {
        return R.layout.fragment_todo_item_add;
    }
    @Override
    protected TodoItemAddViewModel getViewModel() {
        return ViewModelProviders.of(this).get(TodoItemAddViewModel.class);
    }

    @Override
    protected void init() {
        viewModel.setClickInterface(this);
        setHasOptionsMenu(true);
        if (getArguments() != null) {
            viewModel.listId = getArguments().getInt(Constants.Arguments.LIST_ID, -1);
        }
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        menu.findItem(R.id.todoListAddFragment).setVisible(false);
        menu.findItem(R.id.todoItemAddFragment_manuel).setVisible(false);
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public void createClicked() {
        String title = binding.todoItemAddTitle.getText().toString();
        String description = binding.todoItemAddDescription.getText().toString();
        String deadline = binding.todoItemAddDeadline.getText().toString();

        if (viewModel.checkValidation(title, description, deadline)) {
            TodoEntity todoEntity = new TodoEntity();
            todoEntity.setTodolistId(viewModel.listId);
            todoEntity.setName(title);
            todoEntity.setDescription(description);
            todoEntity.setDeadline(deadline);
            todoEntity.setStatus(Constants.TodoStatus.TODO_ACTIVE);

            viewModel.insertTodoEntity(todoEntity);
        }
    }

    @Override
    public void deadlineClicked() {
        Calendar calendar = Calendar.getInstance();
        DatePickerDialog datePickerDialog = new DatePickerDialog(
                context,
                (view, year, month, dayOfMonth) -> {
                    String deadline = (month + 1) +
                            "." + dayOfMonth + "." + year;
                    binding.todoItemAddDeadline.setText(deadline);
                },
                calendar.get(Calendar.YEAR),
                calendar.get(Calendar.MONTH),
                calendar.get(Calendar.DAY_OF_MONTH));
        datePickerDialog.getDatePicker().setMinDate(calendar.getTimeInMillis());
        datePickerDialog.show();
    }
}
