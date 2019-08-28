package net.emrekalkan.todolistapp.di.modules;

import android.content.Context;
import android.content.SharedPreferences;

import net.emrekalkan.todolistapp.TODOListApp;
import net.emrekalkan.todolistapp.db.UserSharedPreferencesHelper;
import net.emrekalkan.todolistapp.utils.Constants;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

@Module
public class ApplicationModule {

    private TODOListApp todoListApp;

    public ApplicationModule(TODOListApp todoListApp) {
        this.todoListApp = todoListApp;
    }

    @Singleton
    @Provides
    TODOListApp providesApp() {
        return todoListApp;
    }

    @Singleton
    @Provides
    Context providesContext() {
        return todoListApp.getApplicationContext();
    }

    @Singleton
    @Provides
    SharedPreferences providesSharedPreferences() {
        return todoListApp.getSharedPreferences(Constants.SharedPreferences.User.USER_PREFERENCES, Context.MODE_PRIVATE);
    }

    @Singleton
    @Provides
    UserSharedPreferencesHelper providesUserSharedPreferencesHelper(SharedPreferences sharedPreferences) {
        return new UserSharedPreferencesHelper(sharedPreferences);
    }
}
