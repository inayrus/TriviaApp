package com.example.triviaapp;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;

public class HighscoreAdapter extends ArrayAdapter {

    // instance variables
    private ArrayList<Highscore> highscores;

    // a constructor
    public HighscoreAdapter(Context context, int resource, ArrayList<Highscore> objects) {
        super(context, resource, objects);

        this.highscores = objects;
    }

    // put the elements in the view
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        // convertView will be made when grid is shown for the first time
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.highscore_item, parent, false);
        }

        Highscore highscore = highscores.get(position);

        TextView rankView = convertView.findViewById(R.id.rankView);
        rankView.setText(String.valueOf(position + 1));

        TextView nameView = convertView.findViewById(R.id.nameView);
        nameView.setText(highscore.getName());

        TextView scoreView = convertView.findViewById(R.id.scoreView);
        int score = highscore.getScore();
        scoreView.setText(String.valueOf(score));

        return convertView;
    }
}

