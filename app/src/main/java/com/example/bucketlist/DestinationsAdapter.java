package com.example.bucketlist;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

// DestinationsAdapter.java
public class DestinationsAdapter extends RecyclerView.Adapter<DestinationsAdapter.ViewHolder> {

    private List<Destination> destinations;
    private OnDeleteClickListener onDeleteClickListener;
    private OnUpdateClickListener onUpdateClickListener;

    public DestinationsAdapter(List<Destination> destinations, OnDeleteClickListener onDeleteClickListener, OnUpdateClickListener onUpdateClickListener) {
        this.destinations = destinations;
        this.onDeleteClickListener = onDeleteClickListener;
        this.onUpdateClickListener = onUpdateClickListener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.destination_item, parent, false);
        return new ViewHolder(view, onDeleteClickListener, onUpdateClickListener);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Destination destination = destinations.get(position);
        holder.countryTextView.setText(destination.country);
        holder.cityTextView.setText(destination.city);
        holder.descriptionTextView.setText(destination.description);

        // Update button click listener
        holder.btnUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int adapterPosition = holder.getAdapterPosition();
                if (onUpdateClickListener != null && adapterPosition != RecyclerView.NO_POSITION) {
                    onUpdateClickListener.onUpdateClick(adapterPosition);
                }
            }
        });

        // Delete button click listener
        holder.btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int adapterPosition = holder.getAdapterPosition();
                if (onDeleteClickListener != null && adapterPosition != RecyclerView.NO_POSITION) {
                    onDeleteClickListener.onDeleteClick(adapterPosition);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return destinations.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView countryTextView;
        TextView cityTextView;
        TextView descriptionTextView;
        Button btnUpdate;
        Button btnDelete;

        public ViewHolder(@NonNull View itemView, OnDeleteClickListener onDeleteClickListener, OnUpdateClickListener onUpdateClickListener) {
            super(itemView);
            countryTextView = itemView.findViewById(R.id.countryTextView);
            cityTextView = itemView.findViewById(R.id.cityTextView);
            descriptionTextView = itemView.findViewById(R.id.descriptionTextView);
            btnUpdate = itemView.findViewById(R.id.btnUpdate);
            btnDelete = itemView.findViewById(R.id.btnDelete);

            // Set click listeners for the buttons
            btnUpdate.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (onUpdateClickListener != null) {
                        onUpdateClickListener.onUpdateClick(getAdapterPosition());
                    }
                }
            });

            btnDelete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (onDeleteClickListener != null) {
                        onDeleteClickListener.onDeleteClick(getAdapterPosition());
                    }
                }
            });
        }
    }

    public interface OnDeleteClickListener {
        void onDeleteClick(int position);
    }

    public interface OnUpdateClickListener {
        void onUpdateClick(int position);
    }
}
