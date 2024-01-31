package com.example.complainantsystemapp;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class ComplaintAdapter extends RecyclerView.Adapter<ComplaintAdapter.ComplaintViewHolder> {

    private List<Complaint> complaintList;
    private OnDeleteClickListener onDeleteClickListener;

    public ComplaintAdapter(List<Complaint> complaintList) {
        this.complaintList = complaintList;
    }

    public interface OnDeleteClickListener {
        void onDeleteClick(int position);
    }

    public void setOnDeleteClickListener(OnDeleteClickListener listener) {
        this.onDeleteClickListener = listener;
    }

    @NonNull
    @Override
    public ComplaintViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_complaint, parent, false);
        return new ComplaintViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ComplaintViewHolder holder, int position) {
        Complaint complaint = complaintList.get(position);

        holder.bind(complaint);
    }

    @Override
    public int getItemCount() {
        return complaintList.size();
    }

    // ViewHolder class
    public class ComplaintViewHolder extends RecyclerView.ViewHolder {

        // Views in your item layout
        private final TextView complaintNameTextView;
        private final TextView complaintTextView;

        private final TextView StringID;
        private final Button deleteButton;

        public ComplaintViewHolder(@NonNull View itemView) {
            super(itemView);
            // Initialize views
            StringID = itemView.findViewById(R.id.idTextView);
            complaintNameTextView = itemView.findViewById(R.id.complaintNameTextView);
            complaintTextView = itemView.findViewById(R.id.complaintTextView);
            deleteButton = itemView.findViewById(R.id.deleteButton);


            // Set click listener for delete button
            deleteButton.setOnClickListener(v -> {
                int position = getAdapterPosition();
                if (position != RecyclerView.NO_POSITION && onDeleteClickListener != null) {
                    onDeleteClickListener.onDeleteClick(position);
                }
            });
        }

        public void bind(Complaint complaint) {
            // Bind data to views
            complaintNameTextView.setText(complaint.getComplaintName());
            complaintTextView.setText(complaint.getComplaint());
            StringID.setText(complaint.getComplaintID());

        }
    }
}
