package com.example.registerandlogin_marnib1_50066761;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Register extends AppCompatActivity {
    private EditText firstNameEditText;
    private EditText lastNameEditText;
    private EditText emailEditText;
    private EditText passwordEditText;
    private EditText dateEditText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        firstNameEditText = findViewById(R.id.firstname);
        lastNameEditText = findViewById(R.id.lastname);
        emailEditText = findViewById(R.id.email);
        passwordEditText = findViewById(R.id.password);
        ImageView datePickerIcon = findViewById(R.id.datePickerIcon);
        dateEditText = findViewById(R.id.dateEditText);

        datePickerIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showDatePickerDialog(dateEditText);
            }
        });

        Button registerButton = findViewById(R.id.button2);
        registerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               //  Perform data validation on button click
                if (validateData()) {
                    // Data is valid, proceed with registration
                    registerUser();
                } else {
                    // Data is invalid, show an error message or handle it accordingly
                    Toast.makeText(Register.this, "Invalid data. Please check your inputs.", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
    private void showDatePickerDialog(final EditText dateEditText) {
        final Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int day = calendar.get(Calendar.DAY_OF_MONTH);

        DatePickerDialog datePickerDialog = new DatePickerDialog(this,
                new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker datePicker, int selectedYear, int selectedMonth, int selectedDay) {
                        // Do something with the selected date
                        @SuppressLint("DefaultLocale") String formattedDate = String.format("%02d/%02d/%04d", selectedMonth + 1, selectedDay, selectedYear);
                        dateEditText.setText(formattedDate);
                    }
                }, year, month, day);

        datePickerDialog.show();
    }
    private boolean validateData() {
        String firstName = firstNameEditText.getText().toString();
        String lastName = lastNameEditText.getText().toString();
        String email = emailEditText.getText().toString();
        String password = passwordEditText.getText().toString();
        String date = dateEditText.getText().toString();

        // Perform individual field validation
        return isValidName(firstName) &&
                isValidName(lastName) &&
                isValidEmail(email) &&
                isValidPassword(password) &&
                isValidDate(date);
    }

    private boolean isValidName(String firstName) {
        return firstName != null && firstName.length() >= 3 && firstName.length() <= 30;
    }


    private boolean isValidEmail(String email) {
        String emailRegex = "^[a-zA-Z0-9_+&*-]+(?:\\.[a-zA-Z0-9_+&*-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,7}$";
        Pattern pattern = Pattern.compile(emailRegex);
        Matcher matcher = pattern.matcher(email);
        return matcher.matches();
    }

    private boolean isValidPassword(String password) {
        return password != null && !password.isEmpty();
    }

    private boolean isValidDate(String date) {
        @SuppressLint("SimpleDateFormat")
        SimpleDateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy");
        dateFormat.setLenient(false);

        try {
            Date parsedDate = dateFormat.parse(date);
            return parsedDate != null;
        } catch (ParseException e) {
            return false;
        }
    }
    private void registerUser() {
        //after registration show the success msg
        Toast.makeText(Register.this, "User registered successfully!", Toast.LENGTH_SHORT).show();
        Intent intent = new Intent(Register.this, MainActivity.class);

        // Start the MainActivity
        startActivity(intent);
    }
}
