package com.example.snake;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Point;

public class Spring extends PowerUps {
    public Spring(Context context, Point sr, int s)
    {
        //Calls PowerUps constructor
        super(sr,s);
        //Load image into bitmap
        Bitmap mBitmapSpring = BitmapFactory.decodeResource(context.getResources(), R.drawable.spring);
        //sets Bitmap to be the bit map for spring
        super.setBitmap(mBitmapSpring);
    }
}

