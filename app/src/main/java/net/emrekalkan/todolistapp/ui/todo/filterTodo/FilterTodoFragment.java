package net.emrekalkan.todolistapp.ui.todo.filterTodo;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;

import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import net.emrekalkan.todolistapp.R;
import net.emrekalkan.todolistapp.core.BaseDiffCallback;
import net.emrekalkan.todolistapp.core.BaseFragment;
import net.emrekalkan.todolistapp.databinding.FragmentFilterTodoBinding;
import net.emrekalkan.todolistapp.ui.main.MainActivity;
import net.emrekalkan.todolistapp.utils.Constants;

import java.util.ArrayList;
import java.util.List;

public class FilterTodoFragment extends BaseFragment<FilterTodoViewModel, FragmentFilterTodoBinding> {

    private int listId = -1;
    private int filterType = -1;
    private int orderType = -1;

    @Override
    protected void init() {
        setHasOptionsMenu(true);
        getBundle();
        setupFilterRecycler();
    }

    @Override
    protected int getLayoutRes() {
        return R.layout.fragment_filter_todo;
    }

    @Override
    protected FilterTodoViewModel getViewModel() {
        return ViewModelProviders.of(this).get(FilterTodoViewModel.class);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        menu.findItem(R.id.todoListAddFragment).setVisible(false);
    }

    private void setupFilterRecycler() {
        FilterTodoRecyclerAdapter adapter = new FilterTodoRecyclerAdapter(
                new BaseDiffCallback<>(),
                this::navigateWithViewType);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(context, RecyclerView.VERTICAL, false);
        DividerItemDecoration itemDecoration = new DividerItemDecoration(context, RecyclerView.VERTICAL);
        binding.todoFilterRecyclerView.addItemDecoration(itemDecoration);
        binding.todoFilterRecyclerView.setLayoutManager(linearLayoutManager);
        binding.todoFilterRecyclerView.setAdapter(adapter);

        List<String> filters = new ArrayList<>();
        filters.add(getString(R.string.all));
        filters.add(getString(R.string.completed));
        filters.add(getString(R.string.active));
        filters.add(getString(R.string.expired));
        adapter.submitList(filters);
    }

    private void getBundle() {
        if (getArguments() != null) {
            Bundle bundle = getArguments();
            listId = bundle.getInt(Constants.Arguments.LIST_ID, -1);
            filterType = bundle.getInt(Constants.TodoItemViewType.FILTER_TYPE);
            orderType = bundle.getInt(Constants.TodoItemViewType.ORDER_TYPE);
        }
    }

    private void navigateWithViewType(String filterName) {
        filterType = viewModel.getViewType(filterName);

        Bundle bundle = new Bundle();
        bundle.putInt(Constants.Arguments.LIST_ID, listId);
        bundle.putInt(Constants.TodoItemViewType.FILTER_TYPE, filterType);
        bundle.putInt(Constants.TodoItemViewType.ORDER_TYPE, orderType);
        ((MainActivity) activity).navigate(R.id.action_filterTodoFragment_to_todoItemFragment, bundle);
    }
}
