package com.example.snake;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import java.util.ArrayList;
import java.util.List;

public class Viewer extends SurfaceView implements Subject{

    private volatile boolean mPaused = true;
    private List<OnTouch> observers = new ArrayList<>();
    private Canvas mCanvas;
    private SurfaceHolder mSurfaceHolder;
    private Paint mPaint;
    private MotionEvent motionEvent;


    Viewer(Context context)
    {
        super(context);
        mSurfaceHolder = getHolder();
        mPaint = new Paint();
    }
    //*Tiaera: private void drawGameOverScreen(Canvas canvas, Paint paint, int finalScore) {
    //    // Draw the game over screen
    //    canvas.drawColor(Color.argb(255, 26, 128, 182));
    //
    //    // Set the size and color of the paint for the text
    //    paint.setColor(Color.argb(255, 255, 255, 255));
    //    paint.setTextSize(120);
    //
    //    // Draw the final score
    //    canvas.drawText("Final Score: " + finalScore, 20, 120, paint);
    //
    //    // Set the size and color of the paint for the options
    //    paint.setTextSize(80);
    //
    //    // Draw the restart option
    //    canvas.drawText("Restart", 200, 400, paint);
    //
    //    // Draw the return to start screen option
    //    canvas.drawText("Return to Start", 200, 600, paint);
    //}
    public void updateViewer(int mScore,Snake mSnake,Apple mApple,List<PowerUps> powerUpsList)
    {
        mCanvas = mSurfaceHolder.lockCanvas();
        if (mSurfaceHolder.getSurface().isValid() && mCanvas !=null) {
            //System.out.println("Valid");
//*Tiaera: if (!mPaused) {

            // Fill the screen with a color
            mCanvas.drawColor(Color.argb(255, 26, 128, 182));

            // Set the size and color of the mPaint for the text

            mPaint.setColor(Color.argb(255, 255, 255, 255));
            mPaint.setTextSize(120);


            // Draw the score
            mCanvas.drawText("" + mScore, 20, 120, mPaint);

            mApple.draw(mCanvas, mPaint);
            mSnake.draw(mCanvas, mPaint);
            for(PowerUps power : powerUpsList)
                power.draw(mCanvas, mPaint);
            //*Tiaera: } else {
//            // Game over screen
//            drawGameOverScreen(mCanvas, mPaint, mScore);
//        }
            // Draw some text while paused
                if (mPaused) {

                // Set the size and color of the mPaint for the text
                mPaint.setColor(Color.argb(255, 255, 255, 255));
                mPaint.setTextSize(250);

                // Draw the message
                // We will give this an international upgrade soon
                mCanvas.drawText("TEST!", 200, 700, mPaint);

                }
            mSurfaceHolder.unlockCanvasAndPost(mCanvas);

        }
    }
    @Override
    public boolean onTouchEvent(MotionEvent motionEvent) {
        this.motionEvent = motionEvent;
        //Tiaera: if (mPaused) {
        //        float x = motionEvent.getX();
        //        float y = motionEvent.getY();
        //
        //        // Check if the touch is within the restart option
        //        if (x >= 200 && x <= 500 && y >= 400 && y <= 480) {
        //            // Restart the game
        //            setPaused(false);
        //            notifyObservers(); // Notify SnakeGame to restart
        //        }
        //
        //        // Check if the touch is within the return to start screen option
        //        if (x >= 200 && x <= 700 && y >= 600 && y <= 680) {
        //            // Handle returning to the start screen (implement as needed)
        //            // You might want to create a method in SnakeActivity to start a new game or return to the start screen.
        //        }
        //    } else {
        notifyObservers();
        return true;
    }
    public boolean getPaused()
    {
        return mPaused;
    }
    public void setPaused(boolean paused)
    {
        mPaused = paused;
    }

    @Override
    public void registerObserver(OnTouch observer)
    {
        observers.add(observer);
    }

    @Override
    public void removeObserver(OnTouch observer)
    {
        observers.remove(observer);
    }

    @Override
    public void notifyObservers()
    {
        for (OnTouch observers : observers) {
            observers.update(motionEvent);
        };
    }
}
