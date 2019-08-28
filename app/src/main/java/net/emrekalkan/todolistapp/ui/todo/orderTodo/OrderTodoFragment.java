package net.emrekalkan.todolistapp.ui.todo.orderTodo;

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
import net.emrekalkan.todolistapp.databinding.FragmentOrderTodoBinding;
import net.emrekalkan.todolistapp.ui.main.MainActivity;
import net.emrekalkan.todolistapp.utils.Constants;

import java.util.ArrayList;
import java.util.List;

public class OrderTodoFragment extends BaseFragment<OrderTodoViewModel, FragmentOrderTodoBinding> {

    private int listId = -1;
    private int filterType = -1;
    private int orderType = -1;

    @Override
    protected int getLayoutRes() {
        return R.layout.fragment_order_todo;
    }

    @Override
    protected OrderTodoViewModel getViewModel() {
        return ViewModelProviders.of(this).get(OrderTodoViewModel.class);
    }

    @Override
    protected void init() {
        setHasOptionsMenu(true);
        getBundle();
        setupFilterRecycler();
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        menu.findItem(R.id.todoListAddFragment).setVisible(false);
    }

    private void setupFilterRecycler() {
        OrderTodoRecyclerAdapter adapter = new OrderTodoRecyclerAdapter(
                new BaseDiffCallback<>(),
                this::navigateWithViewType);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(context, RecyclerView.VERTICAL, false);
        DividerItemDecoration itemDecoration = new DividerItemDecoration(context, RecyclerView.VERTICAL);
        binding.orderTodoRecycler.addItemDecoration(itemDecoration);
        binding.orderTodoRecycler.setLayoutManager(linearLayoutManager);
        binding.orderTodoRecycler.setAdapter(adapter);

        List<String> orderType = new ArrayList<>();
        orderType.add(getString(R.string.createDate));
        orderType.add(getString(R.string.deadline));
        orderType.add(getString(R.string.status));
        orderType.add(getString(R.string.name));
        adapter.submitList(orderType);
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
        orderType = viewModel.getViewType(filterName);

        Bundle bundle = new Bundle();
        bundle.putInt(Constants.Arguments.LIST_ID, listId);
        bundle.putInt(Constants.TodoItemViewType.FILTER_TYPE, filterType);
        bundle.putInt(Constants.TodoItemViewType.ORDER_TYPE, orderType);
        ((MainActivity) activity).navigate(R.id.action_orderTodoFragment_to_todoItemFragment, bundle);
    }
}
