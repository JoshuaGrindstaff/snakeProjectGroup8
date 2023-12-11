/*
package com.example.snake;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Point;

import java.util.Random;

class BadApple implements Collidable {

    private Point location = new Point();
    private Point mSpawnRange;
    private int mSize;
    private Bitmap mBitmapBadApple;

    BadApple(Context context, Point sr, int s) {
        mSpawnRange = sr;
        mSize = s;
        location.x = -10;

        mBitmapBadApple = BitmapFactory.decodeResource(context.getResources(), R.drawable.bad_apple);
        mBitmapBadApple = Bitmap.createScaledBitmap(mBitmapBadApple, s, s, false);
    }

    @Override
    public void spawn() {
        Random random = new Random();
        location.x = random.nextInt(mSpawnRange.x) + 1;
        location.y = random.nextInt(mSpawnRange.y - 1) + 1;
    }

    @Override
    public Point getLocation() {
        return location;
    }

    public void draw(Canvas canvas, Paint paint) {
        canvas.drawBitmap(mBitmapBadApple, location.x * mSize, location.y * mSize, paint);
    }
}
*/
