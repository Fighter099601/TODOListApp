package net.emrekalkan.todolistapp.ui.addList;

import android.view.Menu;
import android.view.MenuInflater;

import androidx.lifecycle.ViewModelProviders;

import net.emrekalkan.todolistapp.R;
import net.emrekalkan.todolistapp.core.BaseFragment;
import net.emrekalkan.todolistapp.databinding.FragmentTodoListAddBinding;
import net.emrekalkan.todolistapp.db.entity.TodoListEntity;
import net.emrekalkan.todolistapp.ui.main.MainActivity;

public class TodoListAddFragment extends BaseFragment<TodoListAddViewModel, FragmentTodoListAddBinding> implements TodoListAddInterface {
    @Override
    protected int getLayoutRes() {
        return R.layout.fragment_todo_list_add;
    }
    @Override
    protected TodoListAddViewModel getViewModel() {
        return ViewModelProviders.of(this).get(TodoListAddViewModel.class);
    }

    @Override
    protected void init() {
        setHasOptionsMenu(true);
        viewModel.setClickInterface(this);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        menu.findItem(R.id.todoListAddFragment).setVisible(false);
        menu.findItem(R.id.todoItemAddFragment_manuel).setVisible(false);
        menu.findItem(R.id.menuDeleteIcon).setVisible(false);
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public void createClicked() {
        String title = binding.todoListAddTitle.getText().toString();
        if (viewModel.checkTitleValidation(title)) {

            TodoListEntity todoListEntity = new TodoListEntity();
            todoListEntity.setName(title);
            todoListEntity.setUserId(viewModel.getUserId());

            viewModel.addTodoList(todoListEntity);

        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ((MainActivity) activity).setMenuItemVisibility(R.id.todoListAddFragment, true);
    }
}