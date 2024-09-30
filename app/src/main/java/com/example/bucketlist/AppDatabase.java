package com.example.bucketlist;

// AppDatabase.java
import androidx.room.Database;
import androidx.room.RoomDatabase;

@Database(entities = {Destination.class}, version = 2)
public abstract class AppDatabase extends RoomDatabase {
    public abstract DestinationDao destinationDao();
}

