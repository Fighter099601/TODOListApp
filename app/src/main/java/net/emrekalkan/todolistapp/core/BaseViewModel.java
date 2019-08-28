package net.emrekalkan.todolistapp.core;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;

import net.emrekalkan.todolistapp.db.UserSharedPreferencesHelper;
import net.emrekalkan.todolistapp.repo.TodoListRepo;
import net.emrekalkan.todolistapp.repo.TodoRepo;
import net.emrekalkan.todolistapp.repo.UserRepo;

import java.lang.ref.WeakReference;

import javax.inject.Inject;

import io.reactivex.disposables.CompositeDisposable;

public class BaseViewModel<I> extends AndroidViewModel {

    @Inject protected UserSharedPreferencesHelper userSharedPreferencesHelper;
    @Inject protected UserRepo userRepo;
    @Inject protected TodoListRepo todoListRepo;
    @Inject protected TodoRepo todoRepo;

    protected CompositeDisposable disposable;
    private WeakReference<I> weakReference;

    protected Application app;

    public BaseViewModel(@NonNull Application application) {
        super(application);
        this.app = application;
        disposable = new CompositeDisposable();
    }

    public void setClickInterface(I clickInterface) {
        weakReference = new WeakReference<>(clickInterface);
    }

    protected I getClickInterface() {
        return weakReference.get();
    }

    @Override
    protected void onCleared() {
        super.onCleared();
        disposable.dispose();
    }
}
