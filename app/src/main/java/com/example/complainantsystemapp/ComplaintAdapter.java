package com.example.complainantsystemapp;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class ComplaintAdapter extends RecyclerView.Adapter<ComplaintAdapter.ComplaintViewHolder> {
    private List<Complaint> complaintList;

    public ComplaintAdapter(List<Complaint> complaintList) {
        this.complaintList = complaintList;
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
        holder.complaintNameTextView.setText(complaint.getComplaintName());
        holder.complaintTextView.setText(complaint.getComplaint());
    }

    @Override
    public int getItemCount() {
        return complaintList.size();
    }

    public static class ComplaintViewHolder extends RecyclerView.ViewHolder {
        TextView complaintNameTextView;
        TextView complaintTextView;

        public ComplaintViewHolder(@NonNull View itemView) {
            super(itemView);
            complaintNameTextView = itemView.findViewById(R.id.complaintNameTextView);
            complaintTextView = itemView.findViewById(R.id.complaintTextView);
        }
    }
}
