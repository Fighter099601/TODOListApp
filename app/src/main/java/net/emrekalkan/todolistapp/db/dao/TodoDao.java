package net.emrekalkan.todolistapp.db.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import net.emrekalkan.todolistapp.db.entity.TodoEntity;

import java.util.List;

@Dao
public interface TodoDao {

    @Insert
    void insertTodo(TodoEntity todoEntity);

    @Update
    void updateTodo(TodoEntity todoEntity);

    @Delete
    void deleteTodo(TodoEntity todoEntity);

    @Query("SELECT * FROM Todo WHERE todolistId=:listId")
    LiveData<List<TodoEntity>> getTodoItems(int listId);

    @Query("DELETE FROM Todo WHERE todolistId in(:listIds)")
    void deleteTodoItemsByListId(List<Integer> listIds);

    @Query("DELETE FROM Todo WHERE id in(:itemIds)")
    void deleteTodoItemsByItemId(List<Integer> itemIds);

    @Query("SELECT * FROM Todo WHERE todolistId=:listId AND status=:status")
    LiveData<List<TodoEntity>> getFilteredTodoItems(int listId, int status);
}
