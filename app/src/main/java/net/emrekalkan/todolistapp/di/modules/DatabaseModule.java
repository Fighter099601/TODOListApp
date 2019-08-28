package net.emrekalkan.todolistapp.di.modules;

import android.content.Context;

import androidx.room.Room;

import net.emrekalkan.todolistapp.db.AppDatabase;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

@Module
public class DatabaseModule {

    @Provides
    @Singleton
    AppDatabase providesDatabase(Context context) {
        return Room.databaseBuilder(context, AppDatabase.class, "Todo-DB").build();
    }
}
