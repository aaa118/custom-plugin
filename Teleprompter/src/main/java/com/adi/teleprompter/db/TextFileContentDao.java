package com.adi.teleprompter.db;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import com.adi.teleprompter.model.TextFile;

import java.util.List;

@Dao
public interface TextFileContentDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    public void insertTextFile(TextFile textFile);

    @Update
    public void updateFileContent(TextFile textFile);

    @Query("SELECT * FROM textFile")
    LiveData<List<TextFile>> getListOfTextFiles();


    @Query("DELETE FROM textfile")
    public void deleteAllTextFiles();
}
