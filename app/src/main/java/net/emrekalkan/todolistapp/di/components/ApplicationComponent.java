package net.emrekalkan.todolistapp.di.components;

import net.emrekalkan.todolistapp.di.modules.ApplicationModule;
import net.emrekalkan.todolistapp.di.modules.DatabaseModule;
import net.emrekalkan.todolistapp.di.modules.RepoModule;
import net.emrekalkan.todolistapp.ui.login.LoginViewModel;
import net.emrekalkan.todolistapp.ui.todolist.TodoListViewModel;
import net.emrekalkan.todolistapp.ui.addTodo.TodoItemAddViewModel;
import net.emrekalkan.todolistapp.ui.addList.TodoListAddViewModel;
import net.emrekalkan.todolistapp.ui.todo.TodoItemViewModel;

import javax.inject.Singleton;

import dagger.Component;

@Singleton
@Component(modules = {ApplicationModule.class, DatabaseModule.class, RepoModule.class})
public interface ApplicationComponent {
    void inject(LoginViewModel loginViewModel);
    void inject(TodoListViewModel todoListViewModel);
    void inject(TodoListAddViewModel todoListAddViewModel);
    void inject(TodoItemViewModel todoItemViewModel);
    void inject(TodoItemAddViewModel todoItemAddViewModel);
}
