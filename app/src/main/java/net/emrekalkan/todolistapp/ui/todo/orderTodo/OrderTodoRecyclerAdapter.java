package net.emrekalkan.todolistapp.ui.todo.orderTodo;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;
import androidx.lifecycle.ViewModel;
import androidx.recyclerview.widget.DiffUtil;

import net.emrekalkan.todolistapp.R;
import net.emrekalkan.todolistapp.TODOListApp;
import net.emrekalkan.todolistapp.core.BaseAdapter;
import net.emrekalkan.todolistapp.core.BaseViewHolder;
import net.emrekalkan.todolistapp.databinding.ItemTodoOrderBinding;
import net.emrekalkan.todolistapp.ui.todo.filterTodo.FilterTodoViewModel;

public class OrderTodoRecyclerAdapter extends BaseAdapter<String> {

    private OrderTodoClickInterface orderTodoClickInterface;

    public OrderTodoRecyclerAdapter(@NonNull DiffUtil.ItemCallback<String> diffCallback, OrderTodoClickInterface orderTodoClickInterface) {
        super(diffCallback);
        this.orderTodoClickInterface = orderTodoClickInterface;
    }

    @Override
    protected ViewDataBinding createBinding(ViewGroup parent, int viewType) {
        ViewDataBinding binding = DataBindingUtil.inflate(
                LayoutInflater.from(parent.getContext()),
                R.layout.item_todo_order,
                parent,
                false
        );
        ViewModel viewModel = new OrderTodoViewModel(((TODOListApp) parent.getContext().getApplicationContext()));
        binding.setVariable(net.emrekalkan.todolistapp.BR.viewModel, viewModel);

        ((ItemTodoOrderBinding) binding).itemTodoOrderName.setOnClickListener(v ->
                orderTodoClickInterface.onOrderClickListener(((ItemTodoOrderBinding) binding).getViewModel().orderName.get()));

        return binding;
    }

    @Override
    protected void bind(ViewDataBinding binding, int position) {
        if (((ItemTodoOrderBinding) binding).getViewModel() != null) {
            ((ItemTodoOrderBinding) binding).getViewModel().orderName.set(getItem(position));
            binding.executePendingBindings();
        }
    }

    @Override
    public void onBindViewHolder(@NonNull BaseViewHolder<ViewDataBinding> holder, int position) {
    }

    public interface OrderTodoClickInterface {
        void onOrderClickListener(String filterName);
    }
}
