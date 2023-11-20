package com.example.snake;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Point;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class PowerUps implements Collidable {
    //Constant variable for number of powerups on screen before no more can spawn
    //location for this power up
    Point location = new Point();
    private Point mSpawnRange;
    private int mSize;
    private Bitmap mBitmapPower;
    private Random random;
    private boolean active;
    public PowerUps(Context context, Point sr, int s)
    {
        mSpawnRange = sr;
        mSize = s;

        location.x = -11;
        //Load images into bitmaps
        mBitmapPower = BitmapFactory.decodeResource(context.getResources(), R.drawable.spring);
        //Resize images
        mBitmapPower = Bitmap.createScaledBitmap(mBitmapPower,mSize,mSize,false);
        random = new Random();
    }
    void spawn(){
        // Choose two random values and place the power up
            location.x = random.nextInt(mSpawnRange.x) + 1;
            location.y = random.nextInt(mSpawnRange.y - 1) + 1;
            active = true;
    }
    // Let SnakeGame know where the power up is
    // SnakeGame can share this with the snake
    @Override
    public Point getLocation()
    {
        return location;
    }
    public boolean getActive()
    {
        return active;
    }
    public void despawn()
    {
        active = false;
        location.x = -11;
    }
    void draw(Canvas canvas, Paint paint){
        canvas.drawBitmap(mBitmapPower,location.x * mSize,location.y * mSize,paint);
    }
}
