package net.emrekalkan.todolistapp.db.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import net.emrekalkan.todolistapp.db.entity.TodoListEntity;

import java.util.List;

@Dao
public interface TodoListDao {

    @Insert
    void insertTodoList(TodoListEntity todoListEntity);

    @Delete
    void deleteTodoList(TodoListEntity todoListEntity);

    @Update
    void updateTodoList(TodoListEntity todoListEntity);

    @Query("SELECT * FROM TodoList WHERE userId=:userId")
    LiveData<List<TodoListEntity>> getTodoLists(int userId);

    @Query("DELETE FROM TodoList WHERE id in(:listIds)")
    void deleteTodoLists(List<Integer> listIds);
}
