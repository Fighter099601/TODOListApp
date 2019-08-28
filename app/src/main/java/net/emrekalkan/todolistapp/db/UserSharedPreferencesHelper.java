package net.emrekalkan.todolistapp.db;

import android.content.SharedPreferences;

import net.emrekalkan.todolistapp.db.entity.UserEntity;
import net.emrekalkan.todolistapp.utils.Constants;

import javax.inject.Inject;

public class UserSharedPreferencesHelper {

    private SharedPreferences sharedPreferences;
    private SharedPreferences.Editor editor;

    @Inject
    public UserSharedPreferencesHelper(SharedPreferences sharedPreferences) {
        this.sharedPreferences = sharedPreferences;
        editor = sharedPreferences.edit();
        editor.apply();
    }

    public int getId() {
        return sharedPreferences.getInt(Constants.SharedPreferences.User.id, -1);
    }

    public void setSharedPreferences(UserEntity userEntity) {
        editor.putInt(Constants.SharedPreferences.User.id, userEntity.getId());
        editor.putString(Constants.SharedPreferences.User.username, userEntity.getUsername());
        editor.putString(Constants.SharedPreferences.User.password, userEntity.getPassword());
        editor.apply();
    }
}
