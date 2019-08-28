package net.emrekalkan.todolistapp.repo;

import android.app.Application;

import androidx.lifecycle.LiveData;

import net.emrekalkan.todolistapp.core.BaseRepo;
import net.emrekalkan.todolistapp.db.AppDatabase;
import net.emrekalkan.todolistapp.db.entity.TodoEntity;

import java.util.List;

public class TodoRepo extends BaseRepo {

    private AppDatabase appDatabase;

    public TodoRepo(Application application, AppDatabase appDatabase) {
        super(application, appDatabase);
        this.appDatabase = appDatabase;
    }

    public LiveData<List<TodoEntity>> getTodoItems(int listId) {
        return appDatabase.todoDao().getTodoItems(listId);
    }

    public void insertTodoItem(TodoEntity todoEntity) {
        appDatabase.todoDao().insertTodo(todoEntity);
    }

    public void deleteTodoItemsByListId(List<Integer> listIds) {
        appDatabase.todoDao().deleteTodoItemsByListId(listIds);
    }

    public void deleteTodoItemsByItemId(List<Integer> itemIds) {
        appDatabase.todoDao().deleteTodoItemsByItemId(itemIds);
    }

    public void updateTodoItemStatus(TodoEntity items) {
        appDatabase.todoDao().updateTodo(items);
    }

    public LiveData<List<TodoEntity>> getFilteredTodoItems(int listId, int status) {
        return appDatabase.todoDao().getFilteredTodoItems(listId, status);
    }
}
