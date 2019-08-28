package net.emrekalkan.todolistapp.db;

import androidx.room.Database;
import androidx.room.RoomDatabase;

import net.emrekalkan.todolistapp.db.dao.TodoDao;
import net.emrekalkan.todolistapp.db.dao.TodoListDao;
import net.emrekalkan.todolistapp.db.dao.UserDao;
import net.emrekalkan.todolistapp.db.entity.TodoEntity;
import net.emrekalkan.todolistapp.db.entity.TodoListEntity;
import net.emrekalkan.todolistapp.db.entity.UserEntity;

@Database(entities = {UserEntity.class, TodoEntity.class, TodoListEntity.class}, version = 1, exportSchema = false)
public abstract class AppDatabase extends RoomDatabase {
    public abstract UserDao userDao();
    public abstract TodoDao todoDao();
    public abstract TodoListDao todoListDao();
}
