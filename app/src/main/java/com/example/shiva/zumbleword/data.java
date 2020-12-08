package com.example.shiva.zumbleword;

/**
 * Created by Shiva on 3/21/2018.
 */

public class data {
    String word1;
    int points;
    public String getWord1() {
        return word1;
    }

    public void setWord1(String word1) {
        this.word1 = word1;
    }

    public int getPoints() {
        return points;
    }

    public void setPoints(int points) {
        this.points = points;
    }
public data(String word,int points)
{
    this.setWord1(word);
    this.setPoints(points);
}
}
