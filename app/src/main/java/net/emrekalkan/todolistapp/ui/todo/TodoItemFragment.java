package net.emrekalkan.todolistapp.ui.todo;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import net.emrekalkan.todolistapp.R;
import net.emrekalkan.todolistapp.core.BaseDiffCallback;
import net.emrekalkan.todolistapp.core.BaseFragment;
import net.emrekalkan.todolistapp.databinding.FragmentTodoItemBinding;
import net.emrekalkan.todolistapp.db.entity.TodoEntity;
import net.emrekalkan.todolistapp.ui.main.MainActivity;
import net.emrekalkan.todolistapp.utils.Constants;
import net.emrekalkan.todolistapp.utils.ViewType;

import java.util.ArrayList;
import java.util.List;

import static net.emrekalkan.todolistapp.utils.Constants.TodoItemViewType.ACTIVE;
import static net.emrekalkan.todolistapp.utils.Constants.TodoItemViewType.ALL;
import static net.emrekalkan.todolistapp.utils.Constants.TodoItemViewType.COMPLETED;
import static net.emrekalkan.todolistapp.utils.Constants.TodoItemViewType.EXPIRED;

public class TodoItemFragment extends BaseFragment<TodoItemViewModel, FragmentTodoItemBinding> implements TodoItemInterface {

    private Menu mainMenu;
    private ArrayList<TodoEntity> selectedTodoItems = new ArrayList<>();

    @Override
    protected int getLayoutRes() {
        return R.layout.fragment_todo_item;
    }
    @Override
    protected TodoItemViewModel getViewModel() {
        return ViewModelProviders.of(this).get(TodoItemViewModel.class);
    }

    @Override
    protected void init() {
        setHasOptionsMenu(true);
        viewModel.setClickInterface(this);
        setupTodoItemList();
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        mainMenu = menu;
        menu.findItem(R.id.todoListAddFragment).setVisible(false);
        menu.findItem(R.id.todoItemAddFragment_manuel).setVisible(true);
        menu.findItem(R.id.menuDeleteIcon).setVisible(false);
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.todoItemAddFragment_manuel) {
            Bundle bundle = new Bundle();
            bundle.putInt(Constants.Arguments.LIST_ID, viewModel.listId);
            ((MainActivity) activity).navigate(R.id.action_todoItemFragment_to_todoItemAddFragment, bundle);
        }else if (item.getItemId() == R.id.menuDeleteIcon) {
            if (selectedTodoItems.size() > 0) {
                List<Integer> itemIds = new ArrayList<>();
                for (TodoEntity e : selectedTodoItems) {
                    itemIds.add(e.getId());
                }
                if (!itemIds.isEmpty()) {
                    viewModel.deleteTodoItemsByItemId(itemIds);
                    showToast(getString(R.string.deleteSuccesful));
                    mainMenu.findItem(R.id.menuDeleteIcon).setVisible(false);
                    selectedTodoItems.clear();
                }
            }
        }
        return super.onOptionsItemSelected(item);
    }

    private void setupTodoItemList() {
        TodoItemRecyclerAdapter adapter = new TodoItemRecyclerAdapter(
                new BaseDiffCallback<>(),
                new TodoItemRecyclerAdapter.TodoItemClickInterface() {
                    @Override
                    public void onTodoCheckBoxChecked(TodoEntity todoEntity) {
                        if (selectedTodoItems.contains(todoEntity))
                            selectedTodoItems.remove(todoEntity);
                        else
                            selectedTodoItems.add(todoEntity);

                        if (selectedTodoItems.size() > 0)
                            mainMenu.findItem(R.id.menuDeleteIcon).setVisible(true);
                        else
                            mainMenu.findItem(R.id.menuDeleteIcon).setVisible(false);
                    }

                    @Override
                    public void markAsComplete(TodoEntity todoEntity) {
                        viewModel.updateTodoItemStatus(viewModel.setStatus(todoEntity));
                    }
                }
        );

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(context, RecyclerView.VERTICAL, false);
        binding.todoItemListRecyclerView.setLayoutManager(linearLayoutManager);
        binding.todoItemListRecyclerView.setAdapter(adapter);

        getTodoItems(adapter);
    }

    @Override
    public void filterByClicked() {
        ((MainActivity) activity).navigate(R.id.action_todoItemFragment_to_filterTodoFragment, viewModel.setBundle());
    }

    @Override
    public void orderByClicked() {
        ((MainActivity) activity).navigate(R.id.action_todoItemFragment_to_orderTodoFragment, viewModel.setBundle());
    }

    private int getViewType() {
        Bundle bundle = getArguments();
        if (bundle != null) {
            viewModel.listId = bundle.getInt(Constants.Arguments.LIST_ID, -1);
            viewModel.filterType = bundle.getInt(Constants.TodoItemViewType.FILTER_TYPE);
            viewModel.orderType = bundle.getInt(Constants.TodoItemViewType.ORDER_TYPE);
            viewModel.filterName = getString(R.string.todoItemFilter) + " (" + ViewType.getFilterName(context, viewModel.filterType) + ")";
            viewModel.orderName = getString(R.string.todoItemOrder) + " (" + ViewType.getOrderName(context, viewModel.orderType) + ")";
            return viewModel.filterType;
            }
        return -1;
    }

    private void getTodoItems(TodoItemRecyclerAdapter adapter) {
        switch (getViewType()) {
            case ALL: getAllTodoItems(adapter); break;
            case COMPLETED: getCompletedTodoItems(adapter); break;
            case ACTIVE: getActiveTodoItems(adapter); break;
            case EXPIRED: getExpiredTodoItems(adapter); break;
        }
    }

    private void getExpiredTodoItems(TodoItemRecyclerAdapter adapter) {
        viewModel.getFilteredTodoItems(viewModel.listId, Constants.TodoStatus.TODO_EXPIRED).observe(
                this,
                todoEntities -> submitToAdapter(adapter, todoEntities)
        );
    }

    private void getActiveTodoItems(TodoItemRecyclerAdapter adapter) {
        viewModel.getFilteredTodoItems(viewModel.listId, Constants.TodoStatus.TODO_ACTIVE).observe(
                this,
                todoEntities -> submitToAdapter(adapter, todoEntities)
        );
    }

    private void getCompletedTodoItems(TodoItemRecyclerAdapter adapter) {
        viewModel.getFilteredTodoItems(viewModel.listId, Constants.TodoStatus.TODO_COMPLETED).observe(
                this,
                todoEntities -> submitToAdapter(adapter, todoEntities)
        );
    }

    private void getAllTodoItems(TodoItemRecyclerAdapter adapter) {
        viewModel.getTodoItemsFromRepo(viewModel.listId).observe(
                this,
                todoEntities -> submitToAdapter(adapter, todoEntities)
        );
    }

    private void submitToAdapter(TodoItemRecyclerAdapter adapter, List<TodoEntity> todoEntities) {
        viewModel.getOrderType(todoEntities);
        adapter.submitList(todoEntities);
        if (!todoEntities.isEmpty())
            viewModel.todoItemEmptyText.set(false);
        else {
            viewModel.todoItemEmptyText.set(true);
        }
        viewModel.todoItemProgress.set(false);
    }
}