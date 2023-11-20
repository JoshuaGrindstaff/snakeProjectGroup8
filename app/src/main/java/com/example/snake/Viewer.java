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
    public void updateViewer(int mScore,Snake mSnake,Apple mApple)
    {
        mCanvas = mSurfaceHolder.lockCanvas();
        if (mSurfaceHolder.getSurface().isValid() && mCanvas !=null) {
            System.out.println("Valid");


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

                }
            mSurfaceHolder.unlockCanvasAndPost(mCanvas);

        }
    }
    @Override
    public boolean onTouchEvent(MotionEvent motionEvent) {
        this.motionEvent = motionEvent;
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
