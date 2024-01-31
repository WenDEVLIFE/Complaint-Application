package com.example.complainantsystemapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;


public class MainActivity extends AppCompatActivity implements  ComplaintAdapter.OnDeleteClickListener {

    private RecyclerView recyclerView;
    private ComplaintAdapter adapter;
    private List<Complaint> complaintList;
    private DatabaseReference myRef;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Get the email of the logged in user from the intent extras
        String email = getIntent().getStringExtra("email");

        TextView textView = findViewById(R.id.email);
        textView.setText("User:"+email);


        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        complaintList = new ArrayList<>();
        adapter = new ComplaintAdapter(complaintList);
        recyclerView.setAdapter(adapter);

        adapter.setOnDeleteClickListener(this); // Ensure MainActivity implements OnDeleteClickListener


        loadComplaintsFromFirebase();

        final Button button = findViewById(R.id.LogoutButton);
        button.setOnClickListener(v -> {
            AlertDialog alertDialog = new AlertDialog.Builder(MainActivity.this).create();
            alertDialog.setTitle("Alert");
            alertDialog.setMessage("Alert message to be shown");
            alertDialog.show();

            Intent intent = new Intent(MainActivity.this, LoginPage.class);
            startActivity(intent);
            finish();
        });

        FloatingActionButton fab = findViewById(R.id.floatingActionButton);
        fab.setOnClickListener(v -> {
            AlertDialog alertDialog = new AlertDialog.Builder(MainActivity.this).create();
            alertDialog.setTitle("Alert");
            alertDialog.setMessage("Alert message for FloatingActionButton");
            alertDialog.show();

            Intent intent = new Intent(MainActivity.this, CreateComplaint.class);
            startActivity(intent);
        });
    }

    private void loadComplaintsFromFirebase() {
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        myRef = database.getReference("complaints");

        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                complaintList.clear();

                for (DataSnapshot complaintSnapshot : dataSnapshot.getChildren()) {
                    String complaintId = complaintSnapshot.getKey();
                    String complaintName = complaintSnapshot.child("complaintName").getValue(String.class);
                    String complaintText = complaintSnapshot.child("complaint").getValue(String.class);
                    complaintList.add(new Complaint(complaintId, "Name:" + complaintName,"Complain message:" + complaintText));
                }

                adapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(DatabaseError error) {
                Toast.makeText(MainActivity.this, "Failed to read data from database", Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public void onDeleteClick(int position) {
        if (position >= 0 && position < complaintList.size()) {
            Complaint deletedComplaint = complaintList.remove(position);
            deleteComplaintFromDatabase(deletedComplaint);
            adapter.notifyItemRemoved(position);
        }
    }

    private void deleteComplaintFromDatabase(Complaint deletedComplaint) {
        String complaintid = deletedComplaint.getComplaintID();

        Log.d("FirebaseDelete", "Deleting complaint with content: " +complaintid);

        myRef.orderByChild("complaintID").equalTo(complaintid).addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                            Log.d("FirebaseDelete", "Found match, deleting: " + snapshot.getValue());
                            snapshot.getRef().removeValue();

                            Toast.makeText(MainActivity.this, "Complaint deleted", Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {
                        Toast.makeText(MainActivity.this, "Failed to delete complaint from database", Toast.LENGTH_SHORT).show();
                    }
                });
    }
}

