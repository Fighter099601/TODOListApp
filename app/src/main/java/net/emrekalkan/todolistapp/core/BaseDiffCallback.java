package net.emrekalkan.todolistapp.core;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DiffUtil;

public class BaseDiffCallback<T> extends DiffUtil.ItemCallback<T> {
    @Override
    public boolean areItemsTheSame(@NonNull T oldItem, @NonNull T newItem) {
        return false;
    }

    @Override
    public boolean areContentsTheSame(@NonNull T oldItem, @NonNull T newItem) {
        return false;
    }
}
