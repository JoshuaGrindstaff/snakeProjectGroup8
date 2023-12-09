package com.example.snake;
import static java.lang.Math.*;
public class GameParameters {
    private int score;
    private boolean spring;
    private boolean gameOver;
    private double spMult;
    private double baseSpMult;
    public GameParameters()
    {
        score = 0;
        spring = false;
        spMult = 0.8;
        baseSpMult = 0.8;
    }
    public void addScore(int add) {score = score + add;}
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
        spMult = 0.8;
        baseSpMult = 0.8;
    }
    public void resetDeath()
    {
        gameOver = false;
        score = 0;
    }
    public void setSpMult(long m){spMult = m;}
    public void addSpMult()
    {
        spMult = spMult + (1/(spMult+2));
        baseSpMult = baseSpMult + 0.1;
    }
    public void updateSpMult()
    {
        if(spMult > baseSpMult)
        {
            spMult = spMult - 0.1;
        }
        else if(spMult < baseSpMult)
        {
            spMult = baseSpMult;
        }
    }
    public double getSpMult(){return spMult;}

}
