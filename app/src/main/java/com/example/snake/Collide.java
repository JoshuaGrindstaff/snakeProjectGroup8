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
                return;
        }

        if(collidable instanceof PowerUps)
        {
            ((PowerUps) collidable).despawn();
            objects.setToRemove(collidable);
            objects.removePowerList((PowerUps) collidable);
            switch(((PowerUps) collidable).getType()) {
                case "Spring":
                    parameters.setSpring();
                    return;
                case "Lightning":
                    parameters.addSpMult();
                default:
                    System.out.println("No PowerUps of Such Type");
            }
        }
    }

}
