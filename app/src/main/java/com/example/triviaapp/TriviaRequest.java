package com.example.triviaapp;

import android.content.Context;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Collections;

public class TriviaRequest implements Response.Listener<JSONObject>, Response.ErrorListener {

    // instance variables
    private Context context;
    private Callback activity;

    // callback interface for the send requests (implemented in main/CategoriesActivity)
    public interface Callback {
        void gotQuestions(ArrayList<Question> question);
        void gotQuestionsError(String message);
    }

    // constructor
    public TriviaRequest(Context context) {
        this.context = context;
    }

    // method that sends the API request
    public void getQuestions(Callback activity, String url) {
        this.activity = activity;

        // request is through a Volley (asynchonrous)
        RequestQueue queue = Volley.newRequestQueue(context);

        // JsonObjectRequest bc the API returns the data as a JSON object
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(url, null, this, this);
        queue.add(jsonObjectRequest);
    }

    // listener method to handle JsonObjectRequest responses
    @Override
    public void onErrorResponse(VolleyError error) {
        activity.gotQuestionsError(error.getMessage());
    }

    @Override
    public void onResponse(JSONObject response) {
        try {
            if (response.getInt("response_code") == 0) {
                // method below accesses the object at top level, which here has "results" as key
                JSONArray jsonArrayQuestions = response.getJSONArray("results");

                // create an arraylist to final Question objects in
                int len = jsonArrayQuestions.length();
                ArrayList<Question> questions = new ArrayList<>(len);

                // read the values from the JSONArray
                for (int i = 0; i < len; i++) {
                    JSONObject item = jsonArrayQuestions.getJSONObject(i);

                    // variables to put all answer values in
                    JSONArray incorrectAnswers = item.getJSONArray("incorrect_answers");
                    int ans_len = incorrectAnswers.length() + 1;
                    ArrayList<String> answers = new ArrayList<>(ans_len);

                    // retrieve the incorrect answer values
                    for (int j = 0, incorrLength = incorrectAnswers.length(); j < incorrLength; j++) {
                        answers.add(incorrectAnswers.getString(j));
                    }
                    // append the correct answer value
                    answers.add(item.getString("correct_answer"));

                    // shuffle the places of the answers
                    Collections.shuffle(answers);

                    // create a Question instance
                    Question question = new Question(item.getString("question"),
                            answers,
                            item.getString("correct_answer"));

                    // append the object to the arraylist with all questions
                    questions.add(question);
                }

                // use callback method to send the first Question object to the activity
                activity.gotQuestions(questions);
            } else {
                activity.gotQuestionsError("Not enough questions in the API");
            }


        } catch (JSONException e) {

            System.out.println("Something went wrong while retrieving the questions");
            e.printStackTrace();
        }
    }
}

