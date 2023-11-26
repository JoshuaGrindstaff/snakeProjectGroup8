package com.example.snake;

public class Collide {
    private Snake snake;
    private GameParameters parameters;
    private Audio sGS;
    public Collide(GameParameters parameters, Snake snake, Audio sGS)
    {
        this.parameters = parameters;
        this.snake = snake;
        this.sGS = sGS;
    }
    public void collide(Collidable collidable)
    {
        if(collidable instanceof Apple)
        {  // This reminds me of Edge of Tomorrow.
                // One day the apple will be ready!
                collidable.spawn();

                // Add to  mScore
                parameters.addScore(1);
                // Add length to snake

                snake.makeLonger();
                // Play a sound
                sGS.playSound(0);
        }
        if(collidable instanceof PowerUps)
        {

        }
    }

}
