package com.example.complainantsystemapp;

public class Complaint {
    private String complaint;
    private String complaintName;

    private String ComplaintID;



    public Complaint(String ComplaintID, String complaintName, String complaint) {
        this.complaint = complaint;
        this.complaintName = complaintName;
        this.ComplaintID = ComplaintID;
    }

    public String getComplaint() {
        return complaint;
    }

    public String getComplaintID() {
        return ComplaintID;
    }

    public void setComplaintID(String ComplaintID) {
        this.ComplaintID = ComplaintID;
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

    public String getContent() {
        return complaint;
    }

    public String getName() {
        return complaintName;
    }
}

