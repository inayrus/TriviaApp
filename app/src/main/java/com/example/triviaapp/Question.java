package com.example.triviaapp;

import android.os.Parcel;
import android.os.Parcelable;
import android.widget.ArrayAdapter;

import java.io.Serializable;
import java.util.ArrayList;

public class Question implements Parcelable{
    // instance attributes
    private String question;
    private ArrayList<String> answers;
    private String correctAnswer;

    // constructor
    public Question(String question, ArrayList<String> answers, String correctAnswer) {
        this.question = question;
        // answers must be put in the list randomly
        this.answers = answers;
        this.correctAnswer = correctAnswer;
    }

    // make object parcable so ArrrayList<Question> can be put in saveOnInstanceState
    private Question(Parcel in) {
        question = in.readString();
        answers = in.createStringArrayList();
        correctAnswer = in.readString();
    }

    public int describeContents() {
        return 0;
    }

    public void writeToParcel(Parcel out, int flags) {
        out.writeString(question);
        out.writeStringList(answers);
        out.writeString(correctAnswer);
    }

    public static final Parcelable.Creator<Question> CREATOR = new Parcelable.Creator<Question>() {
        public Question createFromParcel(Parcel in) {
            return new Question(in);
        }

        public Question[] newArray(int size) {
            return new Question[size];
        }
    };

    // getters
    public String getQuestion() {
        return question;
    }

    public ArrayList<String> getAnswers() {
        return answers;
    }

    public String getCorrectAnswer() {
        return correctAnswer;
    }

    // setters
    public void setQuestion(String question) {
        this.question = question;
    }

    public void setAnswers(ArrayList<String> answers) {
        this.answers = answers;
    }

    public void setCorrectAnswer(String correctAnswer) {
        this.correctAnswer = correctAnswer;
    }
}
