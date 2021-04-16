package com.adi.teleprompter.model;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.NonNull;
import androidx.room.Dao;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import dagger.Provides;

@Entity
public class TextFile implements Parcelable {
    @PrimaryKey
    @NonNull
    private String fileName;
    private String fileContent;

    public TextFile(String fileName, String fileContent) {
        this.fileName = fileName;
        this.fileContent = fileContent;
    }

    protected TextFile(Parcel in) {
        fileName = in.readString();
        fileContent = in.readString();
    }

    public static final Creator<TextFile> CREATOR = new Creator<TextFile>() {
        @Override
        public TextFile createFromParcel(Parcel in) {
            return new TextFile(in);
        }

        @Override
        public TextFile[] newArray(int size) {
            return new TextFile[size];
        }
    };

    public String getFileName() {
        return fileName;
    }

    public String getFileContent() {
        return fileContent;
    }

    @Override
    public String toString() {
        return "TextFile{" +
                "fileName='" + fileName + '\'' +
                ", fileContent='" + fileContent + '\'' +
                '}';
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(fileName);
        dest.writeString(fileContent);
    }
}
