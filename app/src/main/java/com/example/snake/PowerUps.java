package com.example.snake;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Point;

import java.util.Random;

public abstract class PowerUps implements Collidable {
    //Constant variable for number of powerups on screen before no more can spawn
    //location for this power up
    private Point location = new Point();
    private Point mSpawnRange;
    private int mSize;
    private Bitmap mBitmapPowerUp;
    private Random random;
    private Context context;
    public PowerUps(Context context, Point sr, int s)
    {
        this.context = context;
        mSpawnRange = sr;
        mSize = s;
        location.x = -11;

        random = new Random();
    }
    public void spawn(){
        // Choose two random values and place the power up
            location.x = random.nextInt(mSpawnRange.x) + 1;
            location.y = random.nextInt(mSpawnRange.y - 1) + 1;
            //System.out.println("Spawn at \nX:" + location.x +"\nY:" + location.y);
    }
    // Let SnakeGame know where the power up is
    // SnakeGame can share this with the snake
    public void despawn()
    {
        location.x = -11;
    }
    @Override
    public Point getLocation()
    {
        return location;
    }
    protected void setBitmap(Bitmap mBitmapChild)
    {

        mBitmapPowerUp = mBitmapChild;
        mBitmapPowerUp = Bitmap.createScaledBitmap(mBitmapPowerUp,mSize,mSize,false);
    }
    //abstract void draw(Canvas canvas, Paint paint);
    void draw(Canvas canvas, Paint paint){
        if(mBitmapPowerUp != null)
        {
            canvas.drawBitmap(mBitmapPowerUp,location.x * mSize,location.y * mSize,paint);
            //System.out.println("Drawn at \nX:" + location.x +"\nY:" + location.y);
        }
        else
            System.out.println("is null");
    }


}
