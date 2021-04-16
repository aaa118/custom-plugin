package com.adi.teleprompter.db;

import androidx.room.Dao;
import androidx.room.Database;
import androidx.room.RoomDatabase;

import com.adi.teleprompter.model.TextFile;

@Database(entities = {TextFile.class}, exportSchema = false, version = 2)
public abstract class TextFileDatabase extends RoomDatabase {
    public abstract TextFileContentDao textFileContentDao();
}
