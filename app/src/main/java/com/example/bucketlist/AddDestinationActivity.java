package com.example.bucketlist;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.os.AsyncTask;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

public class AddDestinationActivity extends AppCompatActivity {

    private EditText editTextCountry, editTextCity, editTextDescription, editTextInterestingPlaces;
    private Button btnSubmit, btnCapturePicture;
    ImageView imageView;

    private static final int REQUEST_CODE = 27;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_destination);

        // Initialize views
        editTextCountry = findViewById(R.id.editTextCountry);
        editTextCity = findViewById(R.id.editTextCity);
        editTextDescription = findViewById(R.id.editTextDescription);
        btnSubmit = findViewById(R.id.btnSubmit);
        imageView = (ImageView) this.findViewById(R.id.destinationImageView);

        // Set click listener for the submit button
        btnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Handle form submission, e.g., save data to Room Database
                new SaveDestinationTask().execute();
            }
        });

    }


    private class SaveDestinationTask extends AsyncTask<Void, Void, Void> {
        @Override
        protected Void doInBackground(Void... voids) {
            saveDestinationToDatabaseInBackground();
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            // You can perform UI updates if needed after the background task is complete
        }
    }

    private void saveDestinationToDatabaseInBackground() {
        String country = editTextCountry.getText().toString();
        String city = editTextCity.getText().toString();
        String description = editTextDescription.getText().toString();

        if (!country.isEmpty() && !city.isEmpty() && !description.isEmpty()) {
            Destination destination = new Destination();
            destination.country = country;
            destination.city = city;
            destination.description = description;

            // Insert the destination into the Room Database
            MyApplication.database.destinationDao().insert(destination);

            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    Toast.makeText(AddDestinationActivity.this, "Destination added!", Toast.LENGTH_SHORT).show();
                }
            });
        } else {
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    Toast.makeText(AddDestinationActivity.this, "Please fill in all fields", Toast.LENGTH_SHORT).show();
                }
            });
        }
    }
}

