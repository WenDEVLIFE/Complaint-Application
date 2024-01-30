package com.example.complainantsystemapp;

public class Complaint {
    private String complaint;
    private String complaintName;

    public Complaint(String complaint, String complaintName) {
        this.complaint = complaint;
        this.complaintName = complaintName;
    }

    public String getComplaint() {
        return complaint;
    }

    public void setComplaint(String complaint) {
        this.complaint = complaint;
    }

    public String getComplaintName() {
        return complaintName;
    }

    public void setComplaintName(String complaintName) {
        this.complaintName = complaintName;
    }

    @Override
    public String toString() {
        return "Complaint{" +
                "complaint='" + complaint + '\'' +
                ", complaintName='" + complaintName + '\'' +
                '}';
    }
}

