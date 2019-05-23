package com.example.triviaapp;

import java.io.Serializable;

public class Highscore implements Serializable, Comparable<Highscore> {

    // instance variables
    private String name;
    private int score;
    private int dbId;

    // constructor
    public Highscore(String name) {
        this.name = name;
        this.score = 0;
    }

    // second constructor
    public Highscore(String name, int score) {
        this.name = name;
        this.score = score;
    }

    // method to order highscores
    @Override
    public int compareTo(Highscore o) {
        return o.getScore() - this.score;
    }

    public void update(int increase) {
        score += increase;
    }

    // setter
    public void setName(String name) {
        this.name = name;
    }

    public void setDbId(int id) {
        this.dbId = id;
    }

    // getters
    public String getName() {
        return name;
    }

    public int getScore() {
        return score;
    }

    public int getDbId() {
        return dbId;
    }
}
