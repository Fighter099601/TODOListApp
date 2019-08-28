package net.emrekalkan.todolistapp.ui.addTodo;

import android.app.Application;

import androidx.annotation.NonNull;

import net.emrekalkan.todolistapp.R;
import net.emrekalkan.todolistapp.TODOListApp;
import net.emrekalkan.todolistapp.core.BaseViewModel;
import net.emrekalkan.todolistapp.db.entity.TodoEntity;

import io.reactivex.Single;
import io.reactivex.schedulers.Schedulers;

public class TodoItemAddViewModel extends BaseViewModel<TodoItemAddInterface> {
    public TodoItemAddViewModel(@NonNull Application application) {
        super(application);
        ((TODOListApp) application).component.inject(this);
    }

    public int listId = -1;

    public void create() {
        getClickInterface().createClicked();
    }

    public boolean checkValidation(String title, String description, String deadline) {
        if (title.length() < 2) {
            getClickInterface().showToast(getApplication().getString(R.string.todoItemAddTitleFailed));
            return false;
        }

        if (description.length() < 3) {
            getClickInterface().showToast(getApplication().getString(R.string.todoItemAddDescriptionFailed));
            return false;
        }

        if (deadline.isEmpty()) {
            getClickInterface().showToast(getApplication().getString(R.string.todoItemAddDeadlineFailed));
            return false;
        }

        return true;
    }

    public void deadlineClicked() {
        getClickInterface().deadlineClicked();
    }

    public void insertTodoEntity(TodoEntity todoEntity) {
        disposable.add(Single.just(todoRepo)
                .subscribeOn(Schedulers.io())
                .subscribe(todoRepo -> {
                    todoRepo.insertTodoItem(todoEntity);
                    getClickInterface().popBack();
                }));
    }
}
