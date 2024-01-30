package com.example.complainantsystemapp;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private RecyclerView recyclerView;
    private ComplaintAdapter adapter;
    private List<Complaint> complaintList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        complaintList = new ArrayList<>();
        adapter = new ComplaintAdapter(complaintList);
        recyclerView.setAdapter(adapter);

        loadComplaintsFromFirebase();

        final Button button = findViewById(R.id.LogoutButton);
        button.setOnClickListener(v -> {
            // Code here executes on the main thread after the user presses the button
            AlertDialog alertDialog = new AlertDialog.Builder(MainActivity.this).create();
            alertDialog.setTitle("Alert");
            alertDialog.setMessage("Alert message to be shown");
            alertDialog.show();
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
        DatabaseReference myRef = database.getReference("complaints");

        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                complaintList.clear();

                for (DataSnapshot complaintSnapshot : dataSnapshot.getChildren()) {
                    // this is how to display the data in the recycler view
                    String complaintName = complaintSnapshot.getKey(); // Get the complaint name
                    String complaintText = complaintSnapshot.getValue(String.class); // Get the complaint text
                    complaintList.add(new Complaint("Complain message:"+complaintText, "Name:"+complaintName));
                }


                adapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(DatabaseError error) {
                // Handle error
                System.out.println("The read failed: " + error.getCode());
            }
        });
    }

}
