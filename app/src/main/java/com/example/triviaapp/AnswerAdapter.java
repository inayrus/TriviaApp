package com.example.triviaapp;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;

import java.util.ArrayList;

public class AnswerAdapter extends ArrayAdapter {

    // instance variables
    private ArrayList<String> answers;

    // a constructor
    public AnswerAdapter(Context context, int resource, ArrayList<String> objects) {
        super(context, resource, objects);

        this.answers = objects;
    }

    // put the elements in the view
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        // convertView will be made when grid is shown for the first time
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.answer_button_item, parent, false);
        }

        String answer = answers.get(position);

        Button button = convertView.findViewById(R.id.answerButton);
        button.setText(answer);

        // set the answer in the view's tag
        button.setTag(answer);

        return convertView;
    }
}
