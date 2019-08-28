package net.emrekalkan.todolistapp.core;

import android.app.Application;

import net.emrekalkan.todolistapp.db.AppDatabase;

public class BaseRepo {

    protected Application application;
    protected AppDatabase appDatabase;

    public BaseRepo(Application application, AppDatabase appDatabase) {
        this.application = application;
        this.appDatabase = appDatabase;
    }
}
