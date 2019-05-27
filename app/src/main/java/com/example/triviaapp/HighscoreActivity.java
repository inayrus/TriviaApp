package com.example.triviaapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class HighscoreActivity extends AppCompatActivity implements HighscoreRequest.Callback {

    // instance variable
    private Highscore highscore;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_highscore);

        Intent intent = getIntent();
        this.highscore = (Highscore) intent.getSerializableExtra("highscore");

        // check if the highscore requester works
        HighscoreRequest x = new HighscoreRequest(this);
        x.postHighscore(highscore);
        x.getHighscores(this);
    }

    @Override
    public void gotHighscores(ArrayList<Highscore> highscoreList) {

        // display the highscore list with an adapter
        HighscoreAdapter adapter = new HighscoreAdapter(this, R.layout.highscore_item, highscoreList);
        ListView highscoreView = findViewById(R.id.highscoreView);
        highscoreView.setAdapter(adapter);

        // display the user's new score below the list
        String username = highscore.getName();
        int score = highscore.getScore();

        TextView nameView = findViewById(R.id.usernameView);
        nameView.setText(username);

        TextView scoreView = findViewById(R.id.userscoreView);
        scoreView.setText(String.valueOf(score));
    }

    @Override
    public void gotHighscoresError(String message) {
        System.out.println(message);
        Toast.makeText(this, "error message in terminal", Toast.LENGTH_LONG).show();
    }

    // when the user presses the back button
    @Override
    public void onBackPressed()
    {
        super.onBackPressed();

        // send the user to the start screen
        startActivity(new Intent(HighscoreActivity.this, MainActivity.class));
        finish();
    }
}
