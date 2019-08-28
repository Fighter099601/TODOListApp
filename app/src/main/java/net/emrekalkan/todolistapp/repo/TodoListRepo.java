package net.emrekalkan.todolistapp.repo;

import android.app.Application;

import androidx.lifecycle.LiveData;

import net.emrekalkan.todolistapp.core.BaseRepo;
import net.emrekalkan.todolistapp.db.AppDatabase;
import net.emrekalkan.todolistapp.db.entity.TodoListEntity;

import java.util.List;

public class TodoListRepo extends BaseRepo {

    private AppDatabase appDatabase;

    public TodoListRepo(Application application, AppDatabase appDatabase) {
        super(application, appDatabase);
        this.appDatabase = appDatabase;
    }

    public void insertTodoList(TodoListEntity todoListEntity) {
        appDatabase.todoListDao().insertTodoList(todoListEntity);
    }

    public LiveData<List<TodoListEntity>> getUserTodoLists(int userId) {
        return appDatabase.todoListDao().getTodoLists(userId);
    }

    public void deleteTodoLists(List<Integer> listIds) {
        appDatabase.todoListDao().deleteTodoLists(listIds);
    }
}
