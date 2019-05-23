package com.example.triviaapp;

import android.content.Context;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Map;

public class HighscoreRequest implements Response.Listener<JSONArray>, Response.ErrorListener {

    // instance variables
    private Context context;
    private Callback activity;
    private RequestQueue queue;

    // callback interface for the send requests (implemented in highscore activity)
    public interface Callback {
        void gotHighscores(ArrayList<Highscore> highscoreList);
        void gotHighscoresError(String message);
    }

    // constructor
    public HighscoreRequest(Context context) {
        this.context = context;
    }

    // method that sends the API request
    public void getHighscores(Callback activity) {
        this.activity = activity;

        // JsonObjectRequest bc the API returns the data as a JSON object
        String url = "https://ide50-inayrus.legacy.cs50.io:8080/highscores";
        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Request.Method.GET, url, null, this, this);
        queue.add(jsonArrayRequest);
        System.out.println("retrieeve highscores");
    }

    public void postHighscore(Highscore highscore) {
        String url = "https://ide50-inayrus.legacy.cs50.io:8080/highscores";
        this.queue = Volley.newRequestQueue(context);
        HighscorePostRequest request = new HighscorePostRequest(Request.Method.POST, url, this, highscore);
        queue.add(request);
    }

    public void deleteHighscore(Highscore highscore) {
        String url = "https://ide50-inayrus.legacy.cs50.io:8080/highscores";
        HighscorePostRequest request = new HighscorePostRequest(Request.Method.DELETE, url, this, highscore);
        queue.add(request);
    }

    // listener method to handle JsonObjectRequest get responses
    @Override
    public void onErrorResponse(VolleyError error) {
        activity.gotHighscoresError(error.getMessage());
    }

    // parsing the highscores json array
    @Override
    public void onResponse(JSONArray response) {
        System.out.println("onResponse");
        System.out.println(response);

        int len_array = response.length();
        ArrayList<Highscore> highscoreList = new ArrayList<>(len_array);

        // unpack the jsonarray into highscore objects
        for (int i = 0; i < len_array; i++) {
            try {
                // distill the name and score of every element
                JSONObject elem = (JSONObject) response.get(i);
                String name = (String) elem.get("name");
                String sScore = (String) elem.get("score");
                int score = Integer.valueOf(sScore);

                // make them into Highscore objects
                Highscore highscore = new Highscore(name, score);
                highscore.setDbId((int)elem.get("id"));

                highscoreList.add(highscore);
            }
            catch (JSONException e) {
                e.printStackTrace();
            }
        }
        // order the highscores from high to low
        Collections.sort(highscoreList);

        // delete highscores when the list is longer than 10
        if (len_array > 10) {
//            Highscore toDelete = highscoreList.get(len_array - 1);
//
//            deleteHighscore(toDelete);

            // alternate delete: not optimal
            int index = 10;
            while (highscoreList.size() != 10) {
                highscoreList.remove(index);
            }
        }

        // send the highscoreList to the activity
        activity.gotHighscores(highscoreList);
    }
}
