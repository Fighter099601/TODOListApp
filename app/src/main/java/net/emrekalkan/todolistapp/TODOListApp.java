package net.emrekalkan.todolistapp;

import android.app.Application;

import com.facebook.stetho.Stetho;

import net.emrekalkan.todolistapp.di.components.ApplicationComponent;
import net.emrekalkan.todolistapp.di.components.DaggerApplicationComponent;
import net.emrekalkan.todolistapp.di.modules.ApplicationModule;

public class TODOListApp extends Application {

    public ApplicationComponent component;

    @Override
    public void onCreate() {
        super.onCreate();
        component = DaggerApplicationComponent.builder()
                .applicationModule(new ApplicationModule(this))
                .build();

        Stetho.initializeWithDefaults(this);
    }
}