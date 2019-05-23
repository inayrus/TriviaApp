package com.example.triviaapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;

import java.util.Arrays;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // category spinner
        Spinner spinner = (Spinner) findViewById(R.id.spinnerCategories);
        // Create an ArrayAdapter using the string array and a default spinner layout
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.categories, android.R.layout.simple_spinner_item);
        // Specify the layout to use when the list of choices appears
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);

        // difficulty spinner
        Spinner spinnerDiff = (Spinner) findViewById(R.id.spinnerDifficulty);
        // Create an ArrayAdapter using the string array and a default spinner layout
        ArrayAdapter<CharSequence> adapterDiff = ArrayAdapter.createFromResource(this,
                R.array.difficulty, android.R.layout.simple_spinner_item);
        // Specify the layout to use when the list of choices appears
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerDiff.setAdapter(adapterDiff);

    }

    public void startClicked(View v) {

        // a list with all categories and a list with id's
        List<String> categoryList = Arrays.asList(getResources().getStringArray(R.array.categories));
        List<String> categoryId = Arrays.asList(getResources().getStringArray(R.array.categorieId));

        // deducing the category id
        Spinner catSpinner = findViewById(R.id.spinnerCategories);
        String category = catSpinner.getSelectedItem().toString();
        int index = categoryList.indexOf(category);
        String id = categoryId.get(index);

        // make a highscore object with the name
        TextView nameView = findViewById(R.id.chooseName);
        String name = nameView.getText().toString();
        Highscore highscore = new Highscore(name);

        // getting the amount variable
        TextView numQuestionView = findViewById(R.id.numQuestions);
        String numQuestion = numQuestionView.getText().toString();

        // getting the difficulty variable
        Spinner diffSpinner = findViewById(R.id.spinnerDifficulty);
        String difficulty = diffSpinner.getSelectedItem().toString().toLowerCase();

        // making the url
        String placeholders = "https://opentdb.com/api.php?amount=%s&category=%s&difficulty=%s";
        String url = String.format(placeholders, numQuestion, id, difficulty);

        // starting a new activity with the highscore and the url
        Intent intent = new Intent(MainActivity.this, GamePlayActivity.class);
        intent.putExtra("highscore", highscore);
        intent.putExtra("url", url);
        System.out.println(url);
        startActivity(intent);
    }
}
