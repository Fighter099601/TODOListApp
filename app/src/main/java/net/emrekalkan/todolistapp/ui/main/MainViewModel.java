package net.emrekalkan.todolistapp.ui.main;

import android.app.Application;

import androidx.annotation.NonNull;

import net.emrekalkan.todolistapp.core.BaseViewModel;

public class MainViewModel extends BaseViewModel<MainInterface> {
    public MainViewModel(@NonNull Application application) {
        super(application);
    }
}
