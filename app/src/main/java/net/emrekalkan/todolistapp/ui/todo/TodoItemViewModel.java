package net.emrekalkan.todolistapp.ui.todo;

import android.app.Application;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.databinding.ObservableField;
import androidx.lifecycle.LiveData;

import net.emrekalkan.todolistapp.TODOListApp;
import net.emrekalkan.todolistapp.core.BaseViewModel;
import net.emrekalkan.todolistapp.db.entity.TodoEntity;
import net.emrekalkan.todolistapp.utils.Constants;

import java.util.Collections;
import java.util.List;

import io.reactivex.Observable;
import io.reactivex.Single;
import io.reactivex.schedulers.Schedulers;

public class TodoItemViewModel extends BaseViewModel<TodoItemInterface> {
    public TodoItemViewModel(@NonNull Application application) {
        super(application);
        ((TODOListApp) application).component.inject(this);

    }

    public ObservableField<TodoEntity> todoEntity = new ObservableField<>();
    public ObservableField<TodoEntity> getTodoListEntity() {
        return todoEntity;
    }

    ObservableField<Boolean> todoItemEmptyText = new ObservableField<>(false);
    ObservableField<Boolean> todoItemProgress = new ObservableField<>(true);
    public ObservableField<Boolean> getTodoItemEmptyText() {
        return todoItemEmptyText;
    }
    public ObservableField<Boolean> getTodoItemProgress() {
        return todoItemProgress;
    }

    String filterName = "";
    public String getFilterName() {
        return filterName;
    }

    String orderName = "";
    public String getOrderName() {
        return orderName;
    }

    int filterType = 0;
    int orderType = 7;

    int listId = -1;

    public LiveData<List<TodoEntity>> getTodoItemsFromRepo(int listId) {
        return todoRepo.getTodoItems(listId);
    }

    public void deleteTodoItemsByItemId(List<Integer> itemIds) {
        disposable.add(
                Single.just(todoRepo)
                    .subscribeOn(Schedulers.io())
                    .subscribe(todoRepo1 -> todoRepo1.deleteTodoItemsByItemId(itemIds))
        );
    }

    public void updateTodoItemStatus(TodoEntity items) {
        disposable.add(
                Observable.just(todoRepo)
                    .subscribeOn(Schedulers.io())
                    .subscribe(todoRepo1 -> todoRepo1.updateTodoItemStatus(items))
        );
    }

    public void filterByClicked() {
        getClickInterface().filterByClicked();
    }

    public void orderByClicked() {
        getClickInterface().orderByClicked();
    }

    public LiveData<List<TodoEntity>> getFilteredTodoItems(int listId, int status) {
        return todoRepo.getFilteredTodoItems(listId, status);
    }

    private void sortEntitiesByName(List<TodoEntity> todoEntities) {
        Collections.sort(todoEntities, (o1, o2) -> o1.getName().compareToIgnoreCase(o2.getName()));
    }
    private void sortEntitiesByStatus(List<TodoEntity> todoEntities) {
        Collections.sort(todoEntities, (o1, o2) -> Integer.compare(o1.getStatus(), o2.getStatus()));
    }
    private void sortEntitiesByDeadline(List<TodoEntity> todoEntities) {
        Collections.sort(todoEntities, (o1, o2) -> o1.getDeadline().compareToIgnoreCase(o2.getDeadline()));
    }

    public void getOrderType(List<TodoEntity> todoEntities) {
        int mOrderType = orderType;

        if (mOrderType == Constants.TodoItemViewType.STATUS) {
            sortEntitiesByStatus(todoEntities);
        }
        else if (mOrderType == Constants.TodoItemViewType.DEADLINE) {
            sortEntitiesByDeadline(todoEntities);
        }
        else if (mOrderType == Constants.TodoItemViewType.NAME) {
            sortEntitiesByName(todoEntities);
        }
    }

    public TodoEntity setStatus(TodoEntity todoEntity) {
        if (todoEntity.getStatus() == Constants.TodoStatus.TODO_ACTIVE) {
            todoEntity.setStatus(Constants.TodoStatus.TODO_COMPLETED);
        }else {
            todoEntity.setStatus(Constants.TodoStatus.TODO_ACTIVE);
        }

        return todoEntity;
    }

    public Bundle setBundle() {
        Bundle bundle = new Bundle();
        bundle.putInt(Constants.Arguments.LIST_ID, listId);
        bundle.putInt(Constants.TodoItemViewType.FILTER_TYPE, filterType);
        bundle.putInt(Constants.TodoItemViewType.ORDER_TYPE, orderType);
        return bundle;
    }
}