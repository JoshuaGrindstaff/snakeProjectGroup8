package com.example.snake;

public class Collide {
    private Snake snake;
    private GameParameters parameters;
    private Audio sGS;
    private GameObjectLists objects;
    public Collide(GameParameters parameters, Snake snake, Audio sGS, GameObjectLists objects)
    {
        this.parameters = parameters;
        this.snake = snake;
        this.sGS = sGS;
        this.objects = objects;
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
        if(collidable instanceof BadApple)
        {
            collidable.spawn();
            parameters.addScore(-1);
            sGS.playSound(1);
        }

        if(collidable instanceof PowerUps)
        {
            ((PowerUps) collidable).despawn();
            objects.setToRemove(collidable);
            objects.removePowerList((PowerUps) collidable);
            if(collidable instanceof Spring)
            {
                parameters.setSpring();
            }
        }
    }

}
