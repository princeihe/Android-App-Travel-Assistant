package com.example.bucketlist;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class Destination {
    @PrimaryKey(autoGenerate = true)
    public int id;

    public String country;
    public String city;
    public String description;

    @ColumnInfo(name = "image_path")
    private String imagePath;

    public String getCountry() {
        return country;
    }

    public String getCity() {
        return city;
    }

    public String getDescription() {
        return description;
    }

    public int getId() {
        return id;
    }

    public String getImagePath() {
        return imagePath;
    }

    public void setImagePath(String imagePath) {
        this.imagePath = imagePath;
    }
}
