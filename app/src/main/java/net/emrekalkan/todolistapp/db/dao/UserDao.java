package net.emrekalkan.todolistapp.db.dao;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import net.emrekalkan.todolistapp.db.entity.UserEntity;

@Dao
public interface UserDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    public void insertUser(UserEntity userEntity);

    @Update(onConflict = OnConflictStrategy.REPLACE)
    public void updateUser(UserEntity userEntity);

    @Query("SELECT * FROM User WHERE id=:id")
    UserEntity getUser(int id);

    @Query("SELECT username FROM User WHERE username=:username COLLATE NOCASE")
    String getUsername(String username);

    @Query("SELECT * FROM User WHERE username=:username AND password=:password")
    UserEntity checkUserCredentials(String username, String password);
}
