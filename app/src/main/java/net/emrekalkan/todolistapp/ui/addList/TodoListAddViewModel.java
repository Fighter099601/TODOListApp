package net.emrekalkan.todolistapp.ui.addList;

import android.app.Application;

import androidx.annotation.NonNull;

import net.emrekalkan.todolistapp.R;
import net.emrekalkan.todolistapp.TODOListApp;
import net.emrekalkan.todolistapp.core.BaseViewModel;
import net.emrekalkan.todolistapp.db.entity.TodoListEntity;

import io.reactivex.Single;
import io.reactivex.schedulers.Schedulers;

public class TodoListAddViewModel extends BaseViewModel<TodoListAddInterface> {
    public TodoListAddViewModel(@NonNull Application application) {
        super(application);
        ((TODOListApp) application).component.inject(this);
    }

    public void addTodoList(TodoListEntity todoListEntity) {
        disposable.add(
                Single.just(todoListRepo)
                        .subscribeOn(Schedulers.io())
                        .subscribe(todoListRepo -> {
                            todoListRepo.insertTodoList(todoListEntity);
                            getClickInterface().showToast(getApplication().getString(R.string.addTodoListSuccess));
                            getClickInterface().popBack();
                        }, Throwable::printStackTrace)
        );
    }

    public boolean checkTitleValidation(String title) {
        if (title != null && title.length() > 2) {
            return true;
        }else {
            getClickInterface().showToast(getApplication().getString(R.string.addTodoListFailed));
        }

        return false;
    }

    public int getUserId() {
        return userSharedPreferencesHelper.getId();
    }

    public void create() {
        getClickInterface().createClicked();
    }
}
