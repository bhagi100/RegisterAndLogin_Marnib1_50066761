package com.example.registerandlogin_marnib1_50066761;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.Html;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.content.Intent;
import android.content.SharedPreferences;
import java.util.ArrayList;
import java.util.List;
import android.text.TextUtils;
import android.content.Context;

import android.util.Log;
public class Rules extends AppCompatActivity {
    private Button new_game;
    private Button historyButton;
    private static final int REQUEST_CODE_QUIZ = 1;
    private static final String PREF_NAME = "QuizAppPrefs";
    private static final String PREF_SCORE_HISTORY_KEY = "score_history";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rules);
        TextView textView = findViewById(R.id.textView);
        new_game=findViewById(R.id.newGame);


        // Example bulleted data
        String bulletedData ="<ul>" +
                "<li> This game have 5 questions</li>"+
                "<li> Deck of quiz cards with questions and answers.</li>" +
                "<li> Select one or multiple options</li>"+
                "<li> Earn points by answering questions correctly to win.</li>" +
                "<li>Correct answers earn points; incorrect or no answers get none.</li>"+
                "<li>Establish a scoring system (e.g., 1 point per question).</li>"+
                "<li>It displays final score at the end of game</li>"+
                "</ul>";

        // Set the text with bulleted data to the TextView
        textView.setText(Html.fromHtml(bulletedData, Html.FROM_HTML_MODE_LEGACY));
        new_game.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Create an Intent to open the QuizActivity
                Intent intent = new Intent(Rules.this, Quiz.class);

                // Start the Quiz activity page on click
                startActivity(intent);
            }
        });
        historyButton = findViewById(R.id.history);
        historyButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Rules.this, ScoreHistoryActivity.class);
                startActivity(intent);
            }
        });
    }
    // Handle the result from QuizActivity
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_CODE_QUIZ && resultCode == RESULT_OK) {
            int score = data.getIntExtra(Quiz.EXTRA_SCORE, 0);
            Log.d("MainActivity", "Score received: " + score);
            saveScoreToHistory(score);
        }
    }

    private void saveScoreToHistory(int score) {
        List<Integer> scoreHistoryList = getScoreHistory();
        scoreHistoryList.add(score);

        SharedPreferences preferences = getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString(PREF_SCORE_HISTORY_KEY, TextUtils.join(",", scoreHistoryList));
        editor.apply();
    }

    private List<Integer> getScoreHistory() {
        SharedPreferences preferences = getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
        String scoreHistoryString = preferences.getString(PREF_SCORE_HISTORY_KEY, "");
        String[] scoreHistoryArray = scoreHistoryString.split(",");
        List<Integer> scoreHistoryList = new ArrayList<>();

        for (String score : scoreHistoryArray) {
            if (!score.isEmpty()) {
                scoreHistoryList.add(Integer.parseInt(score));
            }
        }

        return scoreHistoryList;
    }
}