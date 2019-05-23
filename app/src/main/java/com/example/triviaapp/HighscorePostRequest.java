package com.example.triviaapp;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

public class HighscorePostRequest extends StringRequest {

    private Map<String, String> params;

    // Constructor
    public HighscorePostRequest(int method, String url, Response.ErrorListener errorListener, Highscore highscore) {
        super(method, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                System.out.println("post listener returned: " + response);
            }
        }, errorListener);

        params = new HashMap<>();

        if(method == Request.Method.POST) {
            params.put("name", highscore.getName());
            params.put("score", String.valueOf(highscore.getScore()));
        }
        else if(method == Request.Method.DELETE) {
            params.put("item_id", String.valueOf(highscore.getDbId()));
        }
    }

    // Method to supply parameters to the request
    @Override
    protected Map<String, String> getParams() {
        return params;
    }
}