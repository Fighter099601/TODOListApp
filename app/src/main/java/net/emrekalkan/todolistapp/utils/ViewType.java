package net.emrekalkan.todolistapp.utils;

import android.content.Context;

import net.emrekalkan.todolistapp.R;

public class ViewType {

    public static String getFilterName(Context context, int type) {
        switch (type) {
            case Constants.TodoItemViewType.ACTIVE:
                return context.getString(R.string.active);
            case Constants.TodoItemViewType.COMPLETED:
                return context.getString(R.string.completed);
            case Constants.TodoItemViewType.EXPIRED:
                return context.getString(R.string.expired);
        }
        return context.getString(R.string.all);
    }

    public static String getOrderName(Context context, int type) {
        switch (type) {

            case Constants.TodoItemViewType.NAME:
                return context.getString(R.string.name);
            case Constants.TodoItemViewType.DEADLINE:
                return context.getString(R.string.deadline);
            case Constants.TodoItemViewType.STATUS:
                return context.getString(R.string.status);
        }
        return context.getString(R.string.createDate);
    }
}
