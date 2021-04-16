package com.adi.teleprompter.di;

import android.content.Context;

import androidx.room.Room;

import com.adi.teleprompter.db.TextFileContentDao;
import com.adi.teleprompter.db.TextFileDatabase;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

@Module
public class DatabaseModule {

    @Provides
    TextFileDatabase provideTextFileDatabase(Context context) {
        String DB_NAME = "Text_File.db";
        return Room.databaseBuilder(
                context,
                TextFileDatabase.class,
                DB_NAME
        ).fallbackToDestructiveMigration().build();
    }

    @Provides
    TextFileContentDao provideTextFileDao(TextFileDatabase textFileDatabase) {
        return textFileDatabase.textFileContentDao();
    }

}
