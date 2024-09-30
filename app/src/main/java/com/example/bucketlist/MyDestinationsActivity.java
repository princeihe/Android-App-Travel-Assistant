package com.example.bucketlist;

// MyDestinationsActivity.java
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.AsyncTask;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import android.widget.ImageView;


import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.bucketlist.MyApplication;
import com.example.bucketlist.R;
import com.example.bucketlist.Destination;

import java.util.List;

// MyDestinationsActivity.java
public class MyDestinationsActivity extends AppCompatActivity implements DestinationsAdapter.OnUpdateClickListener {

    private List<Destination> destinations;
    private ImageView imageView;
    private static final int REQUEST_IMAGE_CAPTURE = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.my_destinations);

        // Execute AsyncTask to fetch and display destinations
        new LoadDestinationsTask().execute();
    }

    private void displayDestinations(List<Destination> destinations) {
        this.destinations = destinations;

        RecyclerView recyclerView = findViewById(R.id.recyclerView);

        // Use the correct constructor for DestinationsAdapter
        DestinationsAdapter adapter = new DestinationsAdapter(destinations, (DestinationsAdapter.OnDeleteClickListener) new DestinationsAdapter.OnDeleteClickListener() {
            @Override
            public void onDeleteClick(int position) {
                // Handle delete button click
                deleteDestination(position);
            }
        }, (DestinationsAdapter.OnUpdateClickListener) this); // Pass 'this' as the OnUpdateClickListener

        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter);
    }


    private class LoadDestinationsTask extends AsyncTask<Void, Void, List<Destination>> {
        @Override
        protected List<Destination> doInBackground(Void... voids) {
            return MyApplication.database.destinationDao().getAllDestinations();
        }

        @Override
        protected void onPostExecute(List<Destination> destinations) {
            displayDestinations(destinations);
        }
    }

    private void deleteDestination(int position) {
        if (destinations != null && position < destinations.size()) {
            Destination destination = destinations.get(position);
            int destinationId = destination.id;

            // Execute AsyncTask to delete the destination in the background
            new DeleteDestinationTask().execute(destinationId);
        }
    }

    private class DeleteDestinationTask extends AsyncTask<Integer, Void, Void> {
        @Override
        protected Void doInBackground(Integer... params) {
            // Delete the destination from the database in the background
            int destinationId = params[0];
            MyApplication.database.destinationDao().deleteById(destinationId);
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            // Refresh the displayed destinations after deletion
            new LoadDestinationsTask().execute();
        }
    }



    @Override
    public void onUpdateClick(int position) {
        if (destinations != null && position < destinations.size()) {
            showUpdateDialog(destinations.get(position));
        }
    }

    // Interface for handling update clicks
    public interface OnUpdateClickListener {
        void onUpdateClick(int position);
    }

    private OnUpdateClickListener onUpdateClickListener;

    // Inside showUpdateDialog method
    private void showUpdateDialog(final Destination destination) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Update Destination");

        View viewInflated = LayoutInflater.from(this).inflate(R.layout.dialog_update_destination, null);
        final EditText editTextCountry = viewInflated.findViewById(R.id.editTextCountry);
        final EditText editTextCity = viewInflated.findViewById(R.id.editTextCity);
        final EditText editTextDescription = viewInflated.findViewById(R.id.editTextDescription);
        Button btnPicture = viewInflated.findViewById(R.id.btnPicture);

        // Set the current values in the EditText fields
        editTextCountry.setText(destination.getCountry());
        editTextCity.setText(destination.getCity());
        editTextDescription.setText(destination.getDescription());

        builder.setView(viewInflated);

        // Handle the "Picture" button click
        btnPicture.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Open the camera
                dispatchTakePictureIntent();
            }
        });

        builder.setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                // Get the updated values from the EditText fields
                String updatedCountry = editTextCountry.getText().toString();
                String updatedCity = editTextCity.getText().toString();
                String updatedDescription = editTextDescription.getText().toString();

                // Update the destination in the database
                updateDestination(destination.getId(), updatedCountry, updatedCity, updatedDescription);

                // Refresh the displayed destinations after updating
                new LoadDestinationsTask().execute();
            }
        });

        builder.setNegativeButton(android.R.string.cancel, null);

        builder.show();
    }



    private void updateDestination(final int destinationId, final String updatedCountry, final String updatedCity, final String updatedDescription) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                // Perform the update operation in the background
                MyApplication.database.destinationDao().updateDestination(destinationId, updatedCountry, updatedCity, updatedDescription);

                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        new LoadDestinationsTask().execute();
                    }
                });
            }
        }).start();
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == RESULT_OK) {
            Bundle extras = data.getExtras();
            if (extras != null) {
                Bitmap imageBitmap = (Bitmap) extras.get("data");
                imageView.setImageBitmap(imageBitmap);
            }
        }
    }

    private void dispatchTakePictureIntent() {
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
            startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE);
        }
    }


}
