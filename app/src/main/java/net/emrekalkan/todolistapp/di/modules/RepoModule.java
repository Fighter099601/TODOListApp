package net.emrekalkan.todolistapp.di.modules;

import net.emrekalkan.todolistapp.TODOListApp;
import net.emrekalkan.todolistapp.db.AppDatabase;
import net.emrekalkan.todolistapp.repo.TodoListRepo;
import net.emrekalkan.todolistapp.repo.TodoRepo;
import net.emrekalkan.todolistapp.repo.UserRepo;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

@Module
public class RepoModule {

    @Singleton
    @Provides
    UserRepo providesUserRepo(TODOListApp application, AppDatabase appDatabase) {
        return new UserRepo(application, appDatabase);
    }

    @Singleton
    @Provides
    TodoListRepo providesTodoListRepo(TODOListApp application, AppDatabase appDatabase) {
        return new TodoListRepo(application, appDatabase);
    }

    @Singleton
    @Provides
    TodoRepo providesTodoRepo(TODOListApp app, AppDatabase appDatabase) {
        return new TodoRepo(app, appDatabase);
    }
}
