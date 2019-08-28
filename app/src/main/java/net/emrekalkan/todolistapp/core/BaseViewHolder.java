package net.emrekalkan.todolistapp.core;

import androidx.databinding.ViewDataBinding;
import androidx.recyclerview.widget.RecyclerView;

public class BaseViewHolder<T extends ViewDataBinding> extends RecyclerView.ViewHolder {

    protected T binding;

    BaseViewHolder(T binding) {
        super(binding.getRoot());
        this.binding = binding;
    }
}
