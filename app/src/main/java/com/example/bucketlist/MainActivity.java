package com.example.bucketlist;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Access the Room database instance
        AppDatabase appDatabase = MyApplication.database;

        // Find the "Add Destination" button by its ID
        Button btnAddDestination = findViewById(R.id.btnAddDestination);

        // Set a click listener for the "Add Destination" button
        btnAddDestination.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Create an Intent to start the AddDestinationActivity
                Intent intent = new Intent(MainActivity.this, AddDestinationActivity.class);
                startActivity(intent);
            }
        });

        // Find the "View on Map" button by its ID
        Button btnViewOnMap = findViewById(R.id.btnViewOnMap);

        // Set a click listener for the "View on Map" button
        btnViewOnMap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Create an Intent to start the MapActivity
                Intent intent = new Intent(MainActivity.this, MapActivity.class);
                startActivity(intent);
            }
        });

        Button btnMyDestinations = findViewById(R.id.btnMyDestinations);

        btnMyDestinations.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Create an Intent to start the MyDestinationsActivity
                Intent intent = new Intent(MainActivity.this, MyDestinationsActivity.class);

                // Start the activity
                startActivity(intent);
            }
        });
    }
}
