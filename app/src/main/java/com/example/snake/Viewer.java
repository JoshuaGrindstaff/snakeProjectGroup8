package com.example.snake;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Point;
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
private Context context;
private Point size;

    Viewer(Context context, Point size)
    {
        super(context);
        mSurfaceHolder = getHolder();
        mPaint = new Paint();
        this.context =  context;
        this.size = size;
    }
    private void drawHighScore(Canvas canvas, Paint paint)
    {
// Draw the game over screen
        canvas.drawColor(Color.argb(255, 26, 128, 182));

        // Set the size and color of the paint for the text
        paint.setColor(Color.argb(255, 255, 255, 255));
        paint.setTextSize(120);

        // Set the size and color of the paint for the options
        paint.setTextSize(80);

        // Draw the restart option
        canvas.drawText("High Score", 50, 100, paint);

        // Draw the return to start screen option
        canvas.drawText("1st...10", 100, 300, paint);
    }
    private void drawGameOverScreen(Canvas canvas, Paint paint, int finalScore, int s) {
//        // Draw the game over screen
//        //canvas.drawColor(Color.argb(255, 26, 128, 182));
//
//        // Set the size and color of the paint for the text
//        //paint.setColor(Color.argb(255, 255, 255, 255));
//        //paint.setTextSize(120);
//
//        // Draw the final score
//        canvas.drawText("Final Score: " + finalScore, 20, 120, paint);
//
//        // Set the size and color of the paint for the options
//        paint.setTextSize(80);
//
//        // Draw the restart option
//        canvas.drawText("Restart", 200, 400, paint);
//
//        // Draw the return to start screen option
//        canvas.drawText("Return to Start", 200, 600, paint);

        // Load the image to the bitmap
        Object mBitmapGameOverScrn = BitmapFactory.decodeResource(context.getResources(), R.drawable.gameoverscrn);

        // Resize the bitmap
        s = 0;
        mBitmapGameOverScrn = Bitmap.createScaledBitmap((Bitmap) mBitmapGameOverScrn, size.x, size.y, false);
        canvas.drawBitmap((Bitmap) mBitmapGameOverScrn,
                 s,s, paint);
   }

    public void updateViewer(GameParameters parameters,Snake mSnake,Apple mApple,BadApple mBadApple,GameObjectLists objects)
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
            mCanvas.drawText("" + parameters.getScore(), 20, 120, mPaint);
            // Draw Multiplier
            mCanvas.drawText("X" + parameters.getMult(), 180, 120, mPaint);

            mApple.draw(mCanvas, mPaint);
            mBadApple.draw(mCanvas, mPaint);
            mSnake.draw(mCanvas, mPaint);
            for(PowerUps power : objects.getPowerList())
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
                if (parameters.getGameOver()){
                    drawGameOverScreen(mCanvas,mPaint, parameters.getScore(),0);
                }
                if(parameters.getShowScore())
                {
                    drawHighScore(mCanvas,mPaint);
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
