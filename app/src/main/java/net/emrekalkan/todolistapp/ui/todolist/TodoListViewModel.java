package net.emrekalkan.todolistapp.ui.todolist;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.databinding.ObservableField;
import androidx.lifecycle.LiveData;

import net.emrekalkan.todolistapp.TODOListApp;
import net.emrekalkan.todolistapp.core.BaseViewModel;
import net.emrekalkan.todolistapp.db.entity.TodoListEntity;

import java.util.List;

import io.reactivex.Single;
import io.reactivex.schedulers.Schedulers;

public class TodoListViewModel extends BaseViewModel<TodoListInterface> {
    public TodoListViewModel(@NonNull Application application) {
        super(application);
        ((TODOListApp) application).component.inject(this);
    }

    public ObservableField<TodoListEntity> todoListEntity = new ObservableField<>();
    public ObservableField<TodoListEntity> getTodoListEntity() {
        return todoListEntity;
    }

    public ObservableField<Boolean> todoListEmptyText = new ObservableField<>(false);
    public ObservableField<Boolean> getTodoListEmptyText() {
        return todoListEmptyText;
    }

    public ObservableField<Boolean> todoListProgress = new ObservableField<>(true);
    public ObservableField<Boolean> getTodoListProgress() {
        return todoListProgress;
    }

    public LiveData<List<TodoListEntity>> getTodoListsFromRepo() {
        int userId = userSharedPreferencesHelper.getId();
        return todoListRepo.getUserTodoLists(userId);
    }

    public void deleteTodoItemsByListId(List<Integer> listIds) {
        disposable.add(Single.just(todoListRepo)
                .subscribeOn(Schedulers.io())
                .subscribe(todoListRepo -> {
                    todoListRepo.deleteTodoLists(listIds);

                    disposable.add(Single.just(todoRepo)
                            .subscribeOn(Schedulers.io())
                            .subscribe(todoRepo1 -> todoRepo1.deleteTodoItemsByListId(listIds)));
                }));
    }
}