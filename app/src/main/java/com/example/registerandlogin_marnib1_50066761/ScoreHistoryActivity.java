package com.example.registerandlogin_marnib1_50066761;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import android.content.Context;
import android.content.SharedPreferences;
import android.widget.TextView;
import java.util.ArrayList;
import java.util.List;
public class ScoreHistoryActivity extends AppCompatActivity {

    private TextView historyTextView;
    private static final String PREF_NAME = "QuizAppPrefs";
    private static final String PREF_SCORE_HISTORY_KEY = "score_history";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_score_history);

        historyTextView = findViewById(R.id.historyTextView);

        displayScoreHistory();
    }

    private void displayScoreHistory() {
        List<Integer> scoreHistoryList = getScoreHistory();
        if (scoreHistoryList.isEmpty()) {
            historyTextView.setText("No score history available.");
        } else {
            historyTextView.setText("Score History:\n\n");
            for (int i = 0; i < scoreHistoryList.size(); i++) {
                historyTextView.append("Game " + (i + 1) + ": " + scoreHistoryList.get(i) + "\n");
            }
        }
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
