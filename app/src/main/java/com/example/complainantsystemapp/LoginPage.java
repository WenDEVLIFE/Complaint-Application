package com.example.complainantsystemapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
public class LoginPage extends AppCompatActivity {
     private EditText editText1;

     private EditText editText2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_page);

        editText1 = findViewById(R.id.editTextTextEmailAddress);
        editText2 = findViewById(R.id.editTextTextPassword);

        final Button button = findViewById(R.id.button);
        button.setOnClickListener(v -> {
            String email = editText1.getText().toString();
            String password = editText2.getText().toString();

            // Check if email and password are not empty
            if (!email.isEmpty() && !password.isEmpty()) {
                FirebaseAuth.getInstance().signInWithEmailAndPassword(email, password)
                        .addOnCompleteListener(LoginPage.this, task -> {
                            if (task.isSuccessful()) {
                                // Sign in success, update UI with the signed-in user's information
                                FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
                                Toast.makeText(LoginPage.this, "Login successful", Toast.LENGTH_SHORT).show();
                                Toast.makeText(LoginPage.this, "Welcome " + user.getEmail(), Toast.LENGTH_SHORT).show();

                                // Start the MainActivity or perform other actions after successful login
                                Intent intent = new Intent(LoginPage.this, MainActivity.class);
                                intent.putExtra("email", user.getEmail());
                                startActivity(intent);
                                finish();
                            } else {
                                // If sign in fails, display a message to the user.
                                Toast.makeText(LoginPage.this, "Authentication failed", Toast.LENGTH_SHORT).show();
                            }
                        });
            } else {
                // Display a message to the user indicating that both email and password are required.
                Toast.makeText(LoginPage.this, "Please enter both email and password", Toast.LENGTH_SHORT).show();
            }
        });
        final CheckBox checkBox = findViewById(R.id.checkBox);
        checkBox.setOnClickListener(v -> {
            // Code here executes on main thread after user presses button
            if(checkBox.isChecked()){

                // This will show the password in plain text
                editText2.setInputType(1);
            } else{

                // This will hide password in plain text
                editText2.setInputType(129);
            }

        });
    }
}