package com.example.snake;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

public class HUD
{
//    private static int DEFAULT_SCORE = 0;
//    private static boolean PAUSE_AT_START = true;
//    private Canvas mCanvas;
//   private SurfaceHolder mSurfaceHolder;
//   private Paint mPaint;


    HUD()
    {

    }

    public void update(boolean mPaused, int mScore,Snake mSnake,Apple mApple,Paint mPaint, SurfaceHolder mSurfaceHolder, Canvas mCanvas)
    {
        // Get a lock on the mCanvas


        if (mSurfaceHolder.getSurface().isValid()) {
            System.out.println("Valid");
            mCanvas = mSurfaceHolder.lockCanvas();

            // Fill the screen with a color
            mCanvas.drawColor(Color.argb(255, 26, 128, 182));

            // Set the size and color of the mPaint for the text
            mPaint.setColor(Color.argb(255, 255, 255, 255));
            mPaint.setTextSize(120);

            // Draw the score
            mCanvas.drawText("" + mScore, 20, 120, mPaint);

            mApple.draw(mCanvas, mPaint);
            mSnake.draw(mCanvas, mPaint);

            // Draw some text while paused
            if (mPaused) {

                // Set the size and color of the mPaint for the text
                mPaint.setColor(Color.argb(255, 255, 255, 255));
                mPaint.setTextSize(250);

                // Draw the message
                // We will give this an international upgrade soon
                mCanvas.drawText("TEST!", 200, 700, mPaint);
                //mCanvas.drawText(getResources().getString(R.string.tap_to_play),200, 700, mPaint);
            }


            // Unlock the mCanvas and reveal the graphics for this frame
            mSurfaceHolder.unlockCanvasAndPost(mCanvas);
        }
    }
}
