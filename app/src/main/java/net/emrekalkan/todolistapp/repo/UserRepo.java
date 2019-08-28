package net.emrekalkan.todolistapp.repo;

import android.app.Application;

import androidx.annotation.Nullable;

import net.emrekalkan.todolistapp.core.BaseRepo;
import net.emrekalkan.todolistapp.db.AppDatabase;
import net.emrekalkan.todolistapp.db.entity.UserEntity;

public class UserRepo extends BaseRepo {

    private AppDatabase appDatabase;

    public UserRepo(@Nullable Application application, AppDatabase appDatabase) {
        super(application, appDatabase);
        this.appDatabase = appDatabase;
    }

    public void insertUser(UserEntity userEntity) {
        appDatabase.userDao().insertUser(userEntity);
    }

    public String checkUsername(String username) {
        return appDatabase.userDao().getUsername(username);
    }

    public UserEntity checkUserCredentials(String username, String password) {
        return appDatabase.userDao().checkUserCredentials(username, password);
    }
}
