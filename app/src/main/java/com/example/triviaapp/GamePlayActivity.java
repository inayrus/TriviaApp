package com.example.triviaapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class GamePlayActivity extends AppCompatActivity implements TriviaRequest.Callback{

    // answers for one question
    private ArrayList<Question> questions;
    private Question question;
    private int indexQuestion;
    private Highscore highscore;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game_play);

        if (savedInstanceState == null) {

            // unpack the send variables
            Intent intent = getIntent();
            this.highscore = (Highscore) intent.getSerializableExtra("highscore");
            String url = intent.getStringExtra("url");

            // create new Trivia request class
            TriviaRequest requester = new TriviaRequest(this);

            // retrieve the questions --> fill the questions specifications here in
            requester.getQuestions(this, url);

        }
        else {
            // restore the saved variables
            this.questions = savedInstanceState.getParcelableArrayList("questions");
            this.indexQuestion = savedInstanceState.getInt("index");
            this.highscore = (Highscore) savedInstanceState.getSerializable("highscore");

            // show the question
            showQuestion("current");
        }
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);

        // save question instance and question requester
        outState.putParcelableArrayList("questions", questions);
        outState.putInt("index", indexQuestion);
        outState.putSerializable("highscore", highscore);
    }

    @Override
    public void gotQuestions(ArrayList<Question> questions) {
        this.indexQuestion = 0;
        this.questions = questions;
        showQuestion("current");
    }

    @Override
    public void gotQuestionsError(String message) {
        Toast.makeText(this, message, Toast.LENGTH_LONG).show();
    }

    private void showQuestion(String currentOrNext) {

        // get the question
        if (currentOrNext.equals("next")) {
            this.question = getNextQuestion();
        } else {
            this.question = getQuestion();
        }

        // set the question in the view
        String ask = question.getQuestion();
        TextView askView = findViewById(R.id.questionText);
        askView.setText(ask);

        // set the answers in the buttons
        ArrayAdapter adapter = new AnswerAdapter(this, R.layout.answer_button_item,
                                                  question.getAnswers());
        ListView answerList = findViewById(R.id.answerButtons);
        answerList.setAdapter(adapter);
    }

    public Question getNextQuestion() {
        indexQuestion += 1;
        return questions.get(indexQuestion);
    }

    public Question getQuestion() {
        return questions.get(indexQuestion);
    }

    public boolean gameCompleted() {
        return indexQuestion + 1 >= questions.size();
    }

    public void answerClicked(View v) {

        // find out what answer was chosen
        Button clicked = (Button) v;
        String clickedAnswer = (String) clicked.getTag();

        // check if the answer is correct
        if(clickedAnswer.equals(question.getCorrectAnswer())) {
            highscore.update(1);
        }

        if(!gameCompleted()) {
            // start new question
            showQuestion("next");
        }
        // go to highscore screen
        else {
            Intent intent = new Intent(GamePlayActivity.this, HighscoreActivity.class);
            intent.putExtra("highscore", highscore);
            startActivity(intent);
        }
    }
}
