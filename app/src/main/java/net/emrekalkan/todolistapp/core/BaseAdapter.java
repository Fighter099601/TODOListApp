package net.emrekalkan.todolistapp.core;

import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.ViewDataBinding;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.ListAdapter;

import net.emrekalkan.todolistapp.R;

import java.util.List;

public abstract class BaseAdapter<T> extends ListAdapter<T, BaseViewHolder<ViewDataBinding>> {
    protected BaseAdapter(@NonNull DiffUtil.ItemCallback<T> diffCallback) {
        super(diffCallback);
    }

    @Override
    public void onBindViewHolder(@NonNull BaseViewHolder<ViewDataBinding> holder, int position, @NonNull List<Object> payloads) {
        holder.binding.getRoot().setTag(R.string.position, position);
        bind(holder.binding, position);
    }

    @NonNull
    @Override
    public BaseViewHolder<ViewDataBinding> onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new BaseViewHolder<>(createBinding(parent, viewType));
    }

    protected abstract ViewDataBinding createBinding(ViewGroup parent, int viewType);
    protected abstract void bind(ViewDataBinding binding, int position);
}
