package com.example.snake;
import static java.lang.Math.*;
public class GameParameters {
    private int score;
    private boolean spring;
    private boolean gameOver;
    private double spMult;
    private double baseSpMult;
    private boolean showScore;
    public GameParameters()
    {
        score = 0;
        spring = false;
        spMult = 0.5;
        baseSpMult = 0.5;
    }
    public void addScore(int add) {
        score = Math.max(0, score + (add * (int)floor(2*baseSpMult))) ;
    }
    public void resetScore() {score = 0;}
    public void setSpring() {spring = true;}
    public void resetSpring(){spring = false;}
    public boolean getSpring(){return spring;}
    public int getScore(){return score;}
    public boolean getGameOver(){return gameOver;}
    public void setDeath()
    {
        gameOver = true;
        spring = false;
        spMult = 0.5;
        baseSpMult = 0.5;
    }
    public void resetDeath()
    {
        gameOver = false;
        score = 0;
    }
    public void setSpMult(long m){spMult = m;}
    public void addSpMult()
    {
        spMult = spMult + (1);
        baseSpMult = baseSpMult + 0.1;
    }
    public void updateSpMult()
    {
        if(spMult > baseSpMult)
        {
            spMult = spMult - 0.05;
        }
        else if(spMult < baseSpMult)
        {
            spMult = baseSpMult;
        }
    }
    public double getSpMult(){return spMult;}

    public boolean getShowScore() {return showScore;}
    public void setShowScore(boolean s) {showScore = s;}
    public int getMult(){return (int)floor(2*baseSpMult);}
}
