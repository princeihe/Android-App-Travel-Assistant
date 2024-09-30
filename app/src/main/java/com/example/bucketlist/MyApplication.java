package com.example.bucketlist;

// MyApplication.java
import android.app.Application;
import androidx.room.Room;

public class MyApplication extends Application {
    public static AppDatabase database;

    @Override
    public void onCreate() {
        super.onCreate();
        database = Room.databaseBuilder(this, AppDatabase.class, "destination-db").build();
    }
}

