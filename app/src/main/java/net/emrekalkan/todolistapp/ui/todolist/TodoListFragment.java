package net.emrekalkan.todolistapp.ui.todolist;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import net.emrekalkan.todolistapp.R;
import net.emrekalkan.todolistapp.core.BaseDiffCallback;
import net.emrekalkan.todolistapp.core.BaseFragment;
import net.emrekalkan.todolistapp.databinding.FragmentTodoListBinding;
import net.emrekalkan.todolistapp.db.entity.TodoListEntity;
import net.emrekalkan.todolistapp.ui.main.MainActivity;
import net.emrekalkan.todolistapp.utils.Constants;

import java.util.ArrayList;
import java.util.List;

public class TodoListFragment extends BaseFragment<TodoListViewModel, FragmentTodoListBinding> implements TodoListInterface {

    @Override
    protected int getLayoutRes() {
        return R.layout.fragment_todo_list;
    }
    @Override
    protected TodoListViewModel getViewModel() {
        return ViewModelProviders.of(this).get(TodoListViewModel.class);
    }

    private Menu mainMenu;
    private ArrayList<TodoListEntity> selectedTodoLists = new ArrayList<>();

    @Override
    protected void init() {
        setHasOptionsMenu(true);
        viewModel.setClickInterface(this);
        setupTodoList();
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        mainMenu = menu;
        menu.findItem(R.id.todoListAddFragment).setVisible(true);
        menu.findItem(R.id.todoItemAddFragment_manuel).setVisible(false);
        menu.findItem(R.id.menuDeleteIcon).setVisible(false);
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.menuDeleteIcon) {
            if (selectedTodoLists.size() > 0) {
                List<Integer> listIds = new ArrayList<>();
                for (TodoListEntity e : selectedTodoLists) {
                    listIds.add(e.getId());
                }
                if (!listIds.isEmpty()) {
                    viewModel.deleteTodoItemsByListId(listIds);
                    showToast(getString(R.string.deleteSuccesful));
                    mainMenu.findItem(R.id.menuDeleteIcon).setVisible(false);
                    selectedTodoLists.clear();
                }
            }
        }
        return super.onOptionsItemSelected(item);
    }

    private void setupTodoList() {
        TodoListRecyclerAdapter adapter = new TodoListRecyclerAdapter(
                new BaseDiffCallback<>(),
                new TodoListRecyclerAdapter.TodoListItemClickListener() {
                    @Override
                    public void onTodoListItemClick(TodoListEntity todoListEntity) {
                        loadTodoItemAddFragment(todoListEntity);
                    }

                    @Override
                    public void onTodoListChecked(TodoListEntity todoListEntity) {
                        handleListCheck(todoListEntity);
                    }
                });

        setupListRecycler(adapter);
    }

    private void loadTodoItemAddFragment(TodoListEntity todoListEntity) {
        Bundle bundle = new Bundle();
        bundle.putInt(Constants.Arguments.LIST_ID, todoListEntity.getId());
        bundle.putInt(Constants.TodoItemViewType.FILTER_TYPE, Constants.TodoItemViewType.ALL);
        bundle.putInt(Constants.TodoItemViewType.ORDER_TYPE, Constants.TodoItemViewType.CREATE_DATE);
        ((MainActivity) activity).navigate(R.id.action_todoListFragment_to_todoItemAddFragment, bundle);
    }

    private void handleListCheck(TodoListEntity todoListEntity) {
        if (selectedTodoLists.contains(todoListEntity)) {
            selectedTodoLists.remove(todoListEntity);
        }else {
            selectedTodoLists.add(todoListEntity);
        }

        if (selectedTodoLists.size() > 0) {
            mainMenu.findItem(R.id.menuDeleteIcon).setVisible(true);
        }else {
            mainMenu.findItem(R.id.menuDeleteIcon).setVisible(false);
        }
    }

    private void setupListRecycler(TodoListRecyclerAdapter adapter) {
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext(), RecyclerView.VERTICAL, false);
        DefaultItemAnimator itemAnimator = new DefaultItemAnimator();
        binding.todolistRecyclerView.setItemAnimator(itemAnimator);
        binding.todolistRecyclerView.setLayoutManager(linearLayoutManager);
        binding.todolistRecyclerView.setAdapter(adapter);

        viewModel.getTodoListsFromRepo().observe(this, todoLists -> {
            adapter.submitList(todoLists);
            if (!todoLists.isEmpty()) {
                viewModel.todoListEmptyText.set(false);
            }else {
                viewModel.todoListEmptyText.set(true);
            }
            viewModel.todoListProgress.set(false);
        });
    }
}
