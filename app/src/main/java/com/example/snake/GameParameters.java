package com.example.snake;

public class GameParameters {
    private int score;
    private boolean spring;
    public GameParameters()
    {
        score = 0;
        spring = false;
    }
    public void addScore(int add) {score = score + add;}
    public void resetScore() {score = 0;}
    public void setSpring() {spring = true;}
    public void resetSpring(){spring = false;}
    public boolean getSpring(){return spring;}
    public int getScore(){return score;}
}
