package com.example.complainantsystemapp;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.UUID;

public class CreateComplaint extends AppCompatActivity {
   TextInputLayout textInputLayout;
   TextInputEditText editText1;
    TextInputLayout textInputLayout1;
    TextInputEditText complaintNameInput1;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_complaint);

        Intent intent1 = getIntent();
        String email = intent1.getStringExtra("email");


        // Get the Intent that started this activity and extract the string
        textInputLayout = findViewById(R.id.textInputLayout1);
        editText1 =  textInputLayout.findViewById(R.id.complaintInput);

        textInputLayout1 = findViewById(R.id.textInputLayout2);
        complaintNameInput1 = textInputLayout1.findViewById(R.id.nameInput);

        textInputLayout1 = findViewById(R.id.textInputLayout2);
        complaintNameInput1 = textInputLayout1.findViewById(R.id.nameInput);

        final Button button = findViewById(R.id.send_data);
        button.setOnClickListener(v -> {
            // Code here executes on the main thread after the user presses the button
            String complaintName = complaintNameInput1.getText().toString();
            String complaint = editText1.getText().toString();

            if (complaintName.isEmpty() || complaint.isEmpty()) {
                complaintNameInput1.setError("Please enter your name");
                complaintNameInput1.requestFocus();
                return;
            } else {
                if (complaintName.length() < 5) {
                    complaintNameInput1.setError("Name should be at least 5 characters long");
                    complaintNameInput1.requestFocus();
                    return;
                } else {
                    try {
                        AlertDialog alertDialog = new AlertDialog.Builder(CreateComplaint.this).create();
                        alertDialog.setTitle("Firebase Message");
                        alertDialog.setMessage("Data sent to Firebase");
                        alertDialog.show();

                        // Generate a unique complaint ID using UUID
                        String complaintId = UUID.randomUUID().toString();

                        // Write the complaint data to the database under the generated complaint ID
                        FirebaseDatabase database = FirebaseDatabase.getInstance();
                        DatabaseReference myRef = database.getReference("complaints");
                        myRef.child(complaintId).setValue(new Complaint(complaintId, complaintName, complaint));

                        // Clear the input fields
                        complaintNameInput1.getText().clear();
                        editText1.getText().clear();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
        });

        final Button button1 = findViewById(R.id.back);
        button1.setOnClickListener(v -> {
            // Code here executes on main thread after user presses button
            finish();
            Intent intent = new Intent(CreateComplaint.this, MainActivity.class);
            startActivity(intent);
            intent.putExtra("email", email);

        });

    }
}