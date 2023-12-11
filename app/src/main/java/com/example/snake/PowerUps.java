package com.example.snake;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Point;

import java.util.Random;

public class PowerUps implements Collidable {
    //Constant variable for number of powers on screen before no more can spawn
    //location for this power up
    private final Point location = new Point();
    private final Point mSpawnRange;
    private final int mSize;
    private Bitmap mBitmapPowerUp;
    private final Random random;
    private final String type;
    private int time;

    public PowerUps(Point sr, int s, Context context)
    {

        mSpawnRange = sr;
        mSize = s;
        location.x = -11;
        time = 600;
        random = new Random();

        switch(random.nextInt(4))
        {
            case 0:
                //Load image into bitmap
                Bitmap mBitmapSpring = BitmapFactory.decodeResource(context.getResources(), R.drawable.spring);
                //sets Bitmap to be the bit map for spring
                mBitmapPowerUp = Bitmap.createScaledBitmap(mBitmapSpring,mSize,mSize,false);
                type = "Spring";
                break;
            case 1:
            case 2:
                //Load image into bitmap
                Bitmap mBitmapLight = BitmapFactory.decodeResource(context.getResources(), R.drawable.lightning);
                //sets Bitmap to be the bit map for Lightning
                mBitmapPowerUp = Bitmap.createScaledBitmap(mBitmapLight,mSize,mSize,false);
                type = "Lightning";
                break;
            case 3:
                //Load image into bitmap
                Bitmap mBitmapBadApple = BitmapFactory.decodeResource(context.getResources(), R.drawable.bad_apple);
                //sets Bitmap to be the bit map for Lightning
                mBitmapPowerUp = Bitmap.createScaledBitmap(mBitmapBadApple,mSize,mSize,false);
                type = "Bad";
                break;
            default:
                //Load image into bitmap
                Bitmap mBitmapDefault = BitmapFactory.decodeResource(context.getResources(), R.drawable.defaultpickup);
                //sets Bitmap to be the bit map default
                mBitmapPowerUp = Bitmap.createScaledBitmap(mBitmapDefault,mSize,mSize,false);
                type = "Default";
        }

    }
    public boolean timer()
    {
        if(time > 0)
        {
            time--;
            return false;
        }
            despawn();
            return true;
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
    public String getType(){return type;}
/*    protected void setBitmap(Bitmap mBitmapChild)
    {

        mBitmapPowerUp = mBitmapChild;
        mBitmapPowerUp = Bitmap.createScaledBitmap(mBitmapPowerUp,mSize,mSize,false);
    }*/
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
