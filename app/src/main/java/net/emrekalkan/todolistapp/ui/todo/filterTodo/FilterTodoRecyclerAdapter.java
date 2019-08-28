package net.emrekalkan.todolistapp.ui.todo.filterTodo;

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
import net.emrekalkan.todolistapp.databinding.ItemTodoFilterBinding;

public class FilterTodoRecyclerAdapter extends BaseAdapter<String> {

    private FilterTodoClickInterface filterTodoClickInterface;

    FilterTodoRecyclerAdapter(@NonNull DiffUtil.ItemCallback<String> diffCallback, FilterTodoClickInterface filterTodoClickInterface) {
        super(diffCallback);
        this.filterTodoClickInterface = filterTodoClickInterface;
    }

    @Override
    protected ViewDataBinding createBinding(ViewGroup parent, int viewType) {
        ViewDataBinding binding = DataBindingUtil.inflate(
                LayoutInflater.from(parent.getContext()),
                R.layout.item_todo_filter,
                parent,
                false
        );
        ViewModel viewModel = new FilterTodoViewModel(((TODOListApp) parent.getContext().getApplicationContext()));
        binding.setVariable(net.emrekalkan.todolistapp.BR.viewModel, viewModel);

        ((ItemTodoFilterBinding) binding).itemTodoFilterName.setOnClickListener(v ->
                filterTodoClickInterface.onFilterClickListener(((ItemTodoFilterBinding) binding).getViewModel().filterName.get()));

        return binding;
    }

    @Override
    protected void bind(ViewDataBinding binding, int position) {
        if (((ItemTodoFilterBinding) binding).getViewModel() != null) {
            ((ItemTodoFilterBinding) binding).getViewModel().filterName.set(getItem(position));
            binding.executePendingBindings();
        }
    }

    @Override
    public void onBindViewHolder(@NonNull BaseViewHolder<ViewDataBinding> holder, int position) {
    }

    public interface FilterTodoClickInterface {
        void onFilterClickListener(String filterName);
    }
}
