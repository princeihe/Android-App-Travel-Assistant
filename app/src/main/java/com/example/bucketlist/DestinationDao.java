package com.example.bucketlist;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

@Dao
public interface DestinationDao {

    @Insert
    void insert(Destination destination);

    @Query("SELECT * FROM Destination")
    List<Destination> getAllDestinations();

    @Query("DELETE FROM Destination WHERE id = :destinationId")
    void deleteById(int destinationId);

    //@Update
    @Query("UPDATE Destination SET country = :updatedCountry, city = :updatedCity, description = :updatedDescription WHERE id = :destinationId")
    void updateDestination(int destinationId, String updatedCountry, String updatedCity, String updatedDescription);
}


