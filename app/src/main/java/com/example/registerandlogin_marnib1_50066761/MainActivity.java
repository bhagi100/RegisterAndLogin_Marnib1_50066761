package com.example.registerandlogin_marnib1_50066761;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class MainActivity extends AppCompatActivity {
    private EditText editText1;
    private EditText editText2;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d("Main", "onCreate called");
        setContentView(R.layout.activity_main);

        editText2 = findViewById(R.id.editText2);
        editText1 = findViewById(R.id.editText1);
        Button openSecondActivityButton = findViewById(R.id.button);
        // onclick of Register button, it opens registration page
        openSecondActivityButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Create an Intent to open the SecondActivity
                Intent intent = new Intent(MainActivity.this, Register.class);

                // Start the Register activity page on click
                startActivity(intent);
            }
        });

        Button loginButton = findViewById(R.id.button2);
        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //  Perform data validation on button click
                if (validateData()) {
                    // Data is valid, proceed with login
                    registerUser();
                } else {
                    // Data is invalid, show an error message or handle it accordingly
                    Toast.makeText(MainActivity.this, "Invalid data. Please check your inputs.", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private boolean validateData() {
        String email = editText2.getText().toString();
        String password = editText1.getText().toString();

        // Perform individual field validation
        return isValidEmail(email) &&
                isValidPassword(password);
    }
    private boolean isValidEmail(String email) {
        //check if email is in correct format
        String emailRegex = "^[a-zA-Z0-9_+&*-]+(?:\\.[a-zA-Z0-9_+&*-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,7}$";
        Pattern pattern = Pattern.compile(emailRegex);
        Matcher matcher = pattern.matcher(email);
        return matcher.matches();
    }

    private boolean isValidPassword(String password) {
        //check if password is not empty
        return password != null && !password.isEmpty();
    }
    private void registerUser() {
        //After successful login show this msg
        Toast.makeText(MainActivity.this, "User signed successfully!", Toast.LENGTH_SHORT).show();
    }
}