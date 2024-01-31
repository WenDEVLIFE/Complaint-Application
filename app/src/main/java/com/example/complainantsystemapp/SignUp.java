package com.example.complainantsystemapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class SignUp extends AppCompatActivity {
    FirebaseAuth mAuth = FirebaseAuth.getInstance();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        EditText editText1 = findViewById(R.id.editTextTextEmailAddress2);
        EditText editText2 = findViewById(R.id.editTextTextPassword2);

        final Button button = findViewById(R.id.button_register);
        button.setOnClickListener(v -> {
            String email= String.valueOf(editText1.getText());
            String password= String.valueOf(editText2.getText());

            // Check if email and password are not empty
            if(email.isEmpty()) {
                editText1.setError("Email is required");
                editText1.requestFocus();
            } else if(password.isEmpty()) {
                editText2.setError("Password is required");
                editText2.requestFocus();
            } else {
                // Perform registration
                if(password.length() < 8) {
                    editText2.setError("Password must be at least 8 characters");
                    editText2.requestFocus();
                } else {
                    // Perform registration
                    // See the createUserWithEmailAndPassword function of FirebaseAuth

                    if (email.endsWith("@complaint.com")) {

                        // Perform registration
                      try {
                          mAuth.createUserWithEmailAndPassword(email, password)
                                  .addOnCompleteListener(this, task -> {
                                      if (task.isSuccessful()) {
                                          // User creation is successful
                                          Log.d("FirebaseAuth", "createUserWithEmail:success");
                                          FirebaseUser user = mAuth.getCurrentUser();
                                          // You can do further actions here, such as updating UI or navigating to another activity

                                          Toast.makeText(SignUp.this, "Registration successful", Toast.LENGTH_SHORT).show();

                                          editText2.setText("");
                                          editText1.setText("");

                                      } else {
                                          // If creation fails, display a message to the user.
                                          Log.w("FirebaseAuth", "createUserWithEmail:failure", task.getException());
                                          Toast.makeText(SignUp.this, "Authentication failed.",
                                                  Toast.LENGTH_SHORT).show();
                                      }
                                  });

                      } catch (IndexOutOfBoundsException e) {
                         e.printStackTrace();
                      }
                    } else {

                        Toast.makeText(SignUp.this, "Please enter a valid email address like test@complaint.com", Toast.LENGTH_SHORT).show();

                    }
                }
            }

        });

        final Button button2 = findViewById(R.id.buttonback);
        button2.setOnClickListener(v -> {
            Intent intent = new Intent(SignUp.this, LoginPage.class);
            startActivity(intent);
        });

        CheckBox checkBox = findViewById(R.id.checkBox2);
        checkBox.setOnCheckedChangeListener((buttonView, isChecked) -> {
            if (isChecked) {
                // Perform action when checked
                // See the setInputType function of EditText
                editText2.setInputType(1);
            } else {
                // Perform action when unchecked
                // See the setInputType function of EditText to normalize the input type
                editText2.setInputType(129);
            }
        });

    }
}