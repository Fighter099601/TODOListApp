package net.emrekalkan.todolistapp.ui.todolist;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;
import androidx.lifecycle.ViewModel;
import androidx.recyclerview.widget.DiffUtil;

import net.emrekalkan.todolistapp.BR;
import net.emrekalkan.todolistapp.R;
import net.emrekalkan.todolistapp.TODOListApp;
import net.emrekalkan.todolistapp.core.BaseAdapter;
import net.emrekalkan.todolistapp.core.BaseViewHolder;
import net.emrekalkan.todolistapp.databinding.ItemTodoListBinding;
import net.emrekalkan.todolistapp.db.entity.TodoListEntity;

public class TodoListRecyclerAdapter extends BaseAdapter<TodoListEntity> {

    private TodoListItemClickListener todoListItemClickListener;

    public TodoListRecyclerAdapter(
            @NonNull DiffUtil.ItemCallback<TodoListEntity> diffCallback,
            TodoListItemClickListener todoListItemClickListener) {
        super(diffCallback);
        this.todoListItemClickListener = todoListItemClickListener;
    }

    @Override
    protected ViewDataBinding createBinding(ViewGroup parent, int viewType) {
        ViewDataBinding binding = DataBindingUtil.inflate   (
                LayoutInflater.from(parent.getContext()),
                R.layout.item_todo_list,
                parent,
                false
        );
        ViewModel viewModel = new TodoListViewModel(((TODOListApp) parent.getContext().getApplicationContext()));
        binding.setVariable(BR.viewModel, viewModel);

        ((ItemTodoListBinding) binding).itemTodoListTitle.setOnClickListener(v -> {
            TodoListViewModel bindingVM = ((ItemTodoListBinding) binding).getViewModel();
            if (bindingVM != null)
                if (bindingVM.todoListEntity.get() != null)
                    todoListItemClickListener.onTodoListItemClick(bindingVM.todoListEntity.get());
        });

        ((ItemTodoListBinding) binding).itemTodoListCheckBox.setOnCheckedChangeListener((buttonView, isChecked) -> {
            TodoListViewModel bindingVM = ((ItemTodoListBinding) binding).getViewModel();
            if (bindingVM != null) {
                TodoListEntity todoListEntity = bindingVM.todoListEntity.get();
                if (todoListEntity != null) {
                    todoListItemClickListener.onTodoListChecked(todoListEntity);
                }
            }
        });

        return binding;
    }

    @Override
    protected void bind(ViewDataBinding binding, int position) {
        if (((ItemTodoListBinding) binding).getViewModel() != null) {
            ((ItemTodoListBinding) binding).getViewModel().todoListEntity.set(getItem(position));
            binding.executePendingBindings();
        }
    }

    @Override
    public void onBindViewHolder(@NonNull BaseViewHolder<ViewDataBinding> holder, int position) {
    }

    @Override
    public int getItemViewType(int position) {
        return position;
    }

    public interface TodoListItemClickListener {
        void onTodoListItemClick(TodoListEntity todoListEntity);
        void onTodoListChecked(TodoListEntity todoListEntity);
    }
}


