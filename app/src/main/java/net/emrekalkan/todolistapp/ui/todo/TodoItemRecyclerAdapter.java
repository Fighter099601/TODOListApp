package net.emrekalkan.todolistapp.ui.todo;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;

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
import net.emrekalkan.todolistapp.databinding.ItemTodoBinding;
import net.emrekalkan.todolistapp.db.entity.TodoEntity;

public class TodoItemRecyclerAdapter extends BaseAdapter<TodoEntity> {

    private TodoItemClickInterface todoItemClickInterface;

    public TodoItemRecyclerAdapter(@NonNull DiffUtil.ItemCallback<TodoEntity> diffCallback, TodoItemClickInterface todoItemClickInterface) {
        super(diffCallback);
        this.todoItemClickInterface = todoItemClickInterface;
    }

    @Override
    protected ViewDataBinding createBinding(ViewGroup parent, int viewType) {
        ViewDataBinding binding = DataBindingUtil.inflate(
                LayoutInflater.from(parent.getContext()),
                R.layout.item_todo,
                parent,
                false
        );
        ViewModel viewModel = new TodoItemViewModel(((TODOListApp) parent.getContext().getApplicationContext()));
        binding.setVariable(BR.viewModel, viewModel);

        ((ItemTodoBinding) binding).itemTodoCheckBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                TodoItemViewModel bindingVM = ((ItemTodoBinding) binding).getViewModel();
                if (bindingVM != null)
                    if (bindingVM.todoEntity!= null)
                        todoItemClickInterface.onTodoCheckBoxChecked(bindingVM.todoEntity.get());
            }
        });

        ((ItemTodoBinding) binding).itemTodoMarkAsCompleted.setOnClickListener(v -> {
            ((ItemTodoBinding) binding).itemTodoMarkAsCompleted.setVisibility(View.GONE);
            ((ItemTodoBinding) binding).itemTodoStatus.setText(R.string.completed);
            TodoItemViewModel bindingVM = ((ItemTodoBinding) binding).getViewModel();
            if (bindingVM != null)
                if (bindingVM.todoEntity!= null)
                    todoItemClickInterface.markAsComplete(bindingVM.todoEntity.get());
        });

        return binding;
    }

    @Override
    protected void bind(ViewDataBinding binding, int position) {
        if (((ItemTodoBinding) binding).getViewModel() != null) {
            ((ItemTodoBinding) binding).getViewModel().todoEntity.set(getItem(position));
            binding.executePendingBindings();
        }
    }

    @Override
    public void onBindViewHolder(@NonNull BaseViewHolder<ViewDataBinding> holder, int position) {
    }

    public interface TodoItemClickInterface {
        void onTodoCheckBoxChecked(TodoEntity todoEntity);
        void markAsComplete(TodoEntity todoEntity);
    }
}
