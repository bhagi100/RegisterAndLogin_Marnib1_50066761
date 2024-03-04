package com.example.registerandlogin_marnib1_50066761;

import androidx.appcompat.app.AppCompatActivity;
import android.util.Log;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.TextView;
import android.widget.Toast;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;

public class Quiz extends AppCompatActivity {
    private TextView questionTextView;
    private CheckBox option1CheckBox, option2CheckBox, option3CheckBox;
    private Button submitButton, nextButton;

    private List<String> questions;
    private List<List<String>> options;
    private List<List<String>> correctAnswers;

    private int currentQuestionIndex = 0;
    private List<List<String>> userAnswers;
    public static final String EXTRA_SCORE = "extraScore";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quiz);

        questionTextView = findViewById(R.id.questionTextView);
        option1CheckBox = findViewById(R.id.option1CheckBox);
        option2CheckBox = findViewById(R.id.option2CheckBox);
        option3CheckBox = findViewById(R.id.option3CheckBox);
        submitButton = findViewById(R.id.submitButton);
        nextButton = findViewById(R.id.nextButton);

        // Initialize questions, options, and correctAnswers lists
        initializeData();

        // Set the initial question
        updateQuestion();

        submitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showConfirmationDialog();
                //submitAnswer();
            }
        });

        nextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                moveToNextQuestion();
            }
        });
    }

    private void initializeData() {
        questions = new ArrayList<>(Arrays.asList(
                "What is the capital of France?",
                "Which planet is known as the Red Planet?",
                "Who wrote 'Romeo and Juliet'?",
                "What is the capital of India?"

        ));

        options = new ArrayList<>(Arrays.asList(
                Arrays.asList("Paris", "Berlin", "London"),
                Arrays.asList("Mars", "Venus", "Jupiter"),
                Arrays.asList("William Shakespeare", "Charles Dickens", "Jane Austen"),
                Arrays.asList("Mumbai", "New Delhi", "Hyderabad")
        ));

        correctAnswers = new ArrayList<>(Arrays.asList(
                Arrays.asList("Paris"),
                Arrays.asList("Mars"),
                Arrays.asList("William Shakespeare"),
                Arrays.asList("New Delhi")

        ));

        // Example of a question with multiple correct answers
        questions.add("Select the programming languages:");
        options.add(Arrays.asList("Java", "C++", "Python"));
        correctAnswers.add(Arrays.asList("Java", "Python"));

        // Initialize userAnswers list
        userAnswers = new ArrayList<>();
        for (int i = 0; i < questions.size(); i++) {
            userAnswers.add(new ArrayList<String>());
        }
    }

    private void updateQuestion() {
        if (currentQuestionIndex < questions.size()) {
            questionTextView.setText("Question " + (currentQuestionIndex + 1) + ": " + questions.get(currentQuestionIndex));

            // Set options dynamically
            List<String> currentOptions = options.get(currentQuestionIndex);
            option1CheckBox.setText(currentOptions.get(0));
            option2CheckBox.setText(currentOptions.get(1));
            option3CheckBox.setText(currentOptions.get(2));

            option1CheckBox.setChecked(false);
            option2CheckBox.setChecked(false);
            option3CheckBox.setChecked(false);

            // Hide the nextButton until the user submits an answer
            nextButton.setVisibility(View.GONE);
        } else {
            // All questions answered, display the result
            displayResult();
        }
    }
    private void showConfirmationDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Confirmation")
                .setMessage("Paris, Are you sure about your answer?")
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        submitAnswer();
                    }
                })
                .setNegativeButton("No", null)
                .show();
    }

    private void submitAnswer() {
        List<String> selectedAnswers = new ArrayList<>();
        if (option1CheckBox.isChecked()) {
            selectedAnswers.add(option1CheckBox.getText().toString());
        }
        if (option2CheckBox.isChecked()) {
            selectedAnswers.add(option2CheckBox.getText().toString());
        }
        if (option3CheckBox.isChecked()) {
            selectedAnswers.add(option3CheckBox.getText().toString());
        }

        // Save the user's answer for the current question
        userAnswers.set(currentQuestionIndex, selectedAnswers);

        // Show the nextButton to allow moving to the next question
        nextButton.setVisibility(View.VISIBLE);

        // Disable checkboxes after submitting an answer
        option1CheckBox.setEnabled(false);
        option2CheckBox.setEnabled(false);
        option3CheckBox.setEnabled(false);

        // Optionally, you can provide feedback to the user here (e.g., Toast)
    }

    private void moveToNextQuestion() {
        // Move to the next question
        currentQuestionIndex++;
        updateQuestion();

        // Re-enable checkboxes for the new question
        option1CheckBox.setEnabled(true);
        option2CheckBox.setEnabled(true);
        option3CheckBox.setEnabled(true);
    }

    private void displayResult() {
        // Calculate and display the result
        int correctCount = 0;

        for (int i = 0; i < questions.size(); i++) {
            if (correctAnswers.get(i).equals(userAnswers.get(i))) {
                correctCount++;
            }
        }

        String resultMessage = "Quiz completed!\nYou got " + correctCount + " out of " + questions.size() + " questions correct.";
        Toast.makeText(this, resultMessage, Toast.LENGTH_LONG).show();
        Intent resultIntent = new Intent();
        resultIntent.putExtra(EXTRA_SCORE, correctCount);
        setResult(RESULT_OK, resultIntent);
        Log.d("QuizActivity", "Score sent: " + correctCount);

        finish();

        // Optionally, you can perform additional actions based on the result
    }
}
